package com.prueba.polizas.service;

import com.prueba.polizas.dto.PolizaResponse;
import com.prueba.polizas.dto.RiesgoRequest;
import com.prueba.polizas.dto.RiesgoResponse;
import com.prueba.polizas.entity.enums.EstadoPoliza;
import com.prueba.polizas.entity.enums.TipoPoliza;

import java.util.List;

public interface PolizaService {

    List<PolizaResponse> listar(TipoPoliza tipo, EstadoPoliza estado);

    List<RiesgoResponse> listarRiesgos(Long polizaId);

    PolizaResponse renovar(Long polizaId);

    PolizaResponse cancelar(Long polizaId);

    RiesgoResponse agregarRiesgo(Long polizaId, RiesgoRequest request);
}
