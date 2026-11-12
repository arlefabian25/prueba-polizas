package com.prueba.polizas.dto;

import com.prueba.polizas.entity.Riesgo;
import com.prueba.polizas.entity.enums.EstadoRiesgo;

import java.time.LocalDate;

public record RiesgoResponse(
        Long id,
        Long polizaId,
        String direccionInmueble,
        String arrendatario,
        String arrendador,
        EstadoRiesgo estado,
        LocalDate fechaInclusion,
        LocalDate fechaCancelacion
) {
    public static RiesgoResponse from(Riesgo riesgo) {
        return new RiesgoResponse(
                riesgo.getId(),
                riesgo.getPoliza().getId(),
                riesgo.getDireccionInmueble(),
                riesgo.getArrendatario(),
                riesgo.getArrendador(),
                riesgo.getEstado(),
                riesgo.getFechaInclusion(),
                riesgo.getFechaCancelacion()
        );
    }
}
