package com.prueba.polizas.service.impl;

import com.prueba.polizas.client.CoreEvent;
import com.prueba.polizas.dto.PolizaResponse;
import com.prueba.polizas.dto.RiesgoRequest;
import com.prueba.polizas.dto.RiesgoResponse;
import com.prueba.polizas.entity.Poliza;
import com.prueba.polizas.entity.Riesgo;
import com.prueba.polizas.entity.enums.EstadoPoliza;
import com.prueba.polizas.entity.enums.EstadoRiesgo;
import com.prueba.polizas.entity.enums.TipoPoliza;
import com.prueba.polizas.exception.BusinessRuleException;
import com.prueba.polizas.exception.ResourceNotFoundException;
import com.prueba.polizas.repository.PolizaRepository;
import com.prueba.polizas.repository.RiesgoRepository;
import com.prueba.polizas.service.PolizaService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

@Service
public class PolizaServiceImpl implements PolizaService {

    private static final BigDecimal CIEN = BigDecimal.valueOf(100);

    private final PolizaRepository polizaRepository;
    private final RiesgoRepository riesgoRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final BigDecimal ipcPorcentaje;

    public PolizaServiceImpl(PolizaRepository polizaRepository,
                             RiesgoRepository riesgoRepository,
                             ApplicationEventPublisher eventPublisher,
                             @Value("${polizas.ipc-porcentaje}") BigDecimal ipcPorcentaje) {
        this.polizaRepository = polizaRepository;
        this.riesgoRepository = riesgoRepository;
        this.eventPublisher = eventPublisher;
        this.ipcPorcentaje = ipcPorcentaje;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PolizaResponse> listar(TipoPoliza tipo, EstadoPoliza estado) {
        return polizaRepository.buscar(tipo, estado).stream()
                .map(PolizaResponse::from)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<RiesgoResponse> listarRiesgos(Long polizaId) {
        buscarPoliza(polizaId);
        return riesgoRepository.findByPolizaIdOrderById(polizaId).stream()
                .map(RiesgoResponse::from)
                .toList();
    }

    @Override
    @Transactional
    public PolizaResponse renovar(Long polizaId) {
        Poliza poliza = buscarPoliza(polizaId);
        if (poliza.getEstado() == EstadoPoliza.CANCELADA) {
            throw new BusinessRuleException("No se puede renovar la póliza " + polizaId + " porque está cancelada");
        }

        // Canon nuevo = canon x (1 + IPC / 100). La prima se recalcula con el canon nuevo,
        // así que también aumenta en el mismo porcentaje del IPC.
        BigDecimal factor = BigDecimal.ONE.add(ipcPorcentaje.divide(CIEN));
        BigDecimal canonNuevo = poliza.getCanon().multiply(factor).setScale(2, RoundingMode.HALF_UP);
        poliza.setCanon(canonNuevo);
        poliza.setPrima(canonNuevo.multiply(BigDecimal.valueOf(poliza.getMesesVigencia())));

        // La renovación es por el mismo periodo de la vigencia inicial.
        LocalDate nuevoInicio = poliza.getFechaFin().plusDays(1);
        poliza.setFechaInicio(nuevoInicio);
        poliza.setFechaFin(nuevoInicio.plusMonths(poliza.getMesesVigencia()).minusDays(1));
        poliza.setEstado(EstadoPoliza.RENOVADA);

        eventPublisher.publishEvent(CoreEvent.actualizacion(polizaId));
        return PolizaResponse.from(poliza);
    }

    @Override
    @Transactional
    public PolizaResponse cancelar(Long polizaId) {
        Poliza poliza = buscarPoliza(polizaId);
        if (poliza.getEstado() == EstadoPoliza.CANCELADA) {
            throw new BusinessRuleException("La póliza " + polizaId + " ya está cancelada");
        }

        LocalDate hoy = LocalDate.now();
        poliza.setEstado(EstadoPoliza.CANCELADA);
        poliza.setFechaCancelacion(hoy);

        // La cancelación de una póliza cancela todos sus riesgos activos.
        for (Riesgo riesgo : riesgoRepository.findByPolizaIdAndEstado(polizaId, EstadoRiesgo.ACTIVO)) {
            riesgo.setEstado(EstadoRiesgo.CANCELADO);
            riesgo.setFechaCancelacion(hoy);
        }

        eventPublisher.publishEvent(CoreEvent.actualizacion(polizaId));
        return PolizaResponse.from(poliza);
    }

    @Override
    @Transactional
    public RiesgoResponse agregarRiesgo(Long polizaId, RiesgoRequest request) {
        Poliza poliza = buscarPoliza(polizaId);
        if (poliza.getTipo() != TipoPoliza.COLECTIVA) {
            throw new BusinessRuleException("Solo se pueden agregar riesgos a pólizas colectivas. "
                    + "Una póliza individual solo puede tener 1 riesgo");
        }
        if (poliza.getEstado() == EstadoPoliza.CANCELADA) {
            throw new BusinessRuleException("No se pueden agregar riesgos a la póliza " + polizaId
                    + " porque está cancelada");
        }

        Riesgo riesgo = new Riesgo();
        riesgo.setPoliza(poliza);
        riesgo.setDireccionInmueble(request.direccionInmueble().trim());
        riesgo.setArrendatario(request.arrendatario().trim());
        riesgo.setArrendador(request.arrendador().trim());
        riesgo.setEstado(EstadoRiesgo.ACTIVO);
        riesgo.setFechaInclusion(LocalDate.now());
        Riesgo guardado = riesgoRepository.save(riesgo);

        eventPublisher.publishEvent(CoreEvent.actualizacion(polizaId));
        return RiesgoResponse.from(guardado);
    }

    private Poliza buscarPoliza(Long polizaId) {
        return polizaRepository.findById(polizaId)
                .orElseThrow(() -> new ResourceNotFoundException("La póliza " + polizaId + " no existe"));
    }
}
