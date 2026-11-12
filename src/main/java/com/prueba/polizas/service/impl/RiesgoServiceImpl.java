package com.prueba.polizas.service.impl;

import com.prueba.polizas.client.CoreEvent;
import com.prueba.polizas.dto.RiesgoResponse;
import com.prueba.polizas.entity.Riesgo;
import com.prueba.polizas.entity.enums.EstadoPoliza;
import com.prueba.polizas.entity.enums.EstadoRiesgo;
import com.prueba.polizas.entity.enums.TipoPoliza;
import com.prueba.polizas.exception.BusinessRuleException;
import com.prueba.polizas.exception.ResourceNotFoundException;
import com.prueba.polizas.repository.RiesgoRepository;
import com.prueba.polizas.service.RiesgoService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class RiesgoServiceImpl implements RiesgoService {

    private final RiesgoRepository riesgoRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public RiesgoResponse cancelar(Long riesgoId) {
        Riesgo riesgo = riesgoRepository.findById(riesgoId)
                .orElseThrow(() -> new ResourceNotFoundException("El riesgo " + riesgoId + " no existe"));

        if (riesgo.getEstado() == EstadoRiesgo.CANCELADO) {
            throw new BusinessRuleException("El riesgo " + riesgoId + " ya está cancelado");
        }
        if (riesgo.getPoliza().getEstado() == EstadoPoliza.CANCELADA) {
            throw new BusinessRuleException("La póliza del riesgo " + riesgoId + " está cancelada");
        }
        // Una individual tiene exactamente 1 riesgo: cancelarlo dejaría la póliza sin cobertura.
        if (riesgo.getPoliza().getTipo() == TipoPoliza.INDIVIDUAL) {
            throw new BusinessRuleException("El riesgo de una póliza individual no se cancela por separado. "
                    + "Cancele la póliza " + riesgo.getPoliza().getId());
        }

        riesgo.setEstado(EstadoRiesgo.CANCELADO);
        riesgo.setFechaCancelacion(LocalDate.now());

        eventPublisher.publishEvent(CoreEvent.actualizacion(riesgo.getPoliza().getId()));
        return RiesgoResponse.from(riesgo);
    }
}
