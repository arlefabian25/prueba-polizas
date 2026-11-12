package com.prueba.polizas.dto;

import com.prueba.polizas.entity.Poliza;
import com.prueba.polizas.entity.enums.EstadoPoliza;
import com.prueba.polizas.entity.enums.TipoPoliza;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PolizaResponse(
        Long id,
        String numeroPoliza,
        TipoPoliza tipo,
        EstadoPoliza estado,
        String tomador,
        BigDecimal canon,
        BigDecimal prima,
        Integer mesesVigencia,
        LocalDate fechaInicio,
        LocalDate fechaFin,
        LocalDate fechaCancelacion
) {
    public static PolizaResponse from(Poliza poliza) {
        return new PolizaResponse(
                poliza.getId(),
                poliza.getNumeroPoliza(),
                poliza.getTipo(),
                poliza.getEstado(),
                poliza.getTomador(),
                poliza.getCanon(),
                poliza.getPrima(),
                poliza.getMesesVigencia(),
                poliza.getFechaInicio(),
                poliza.getFechaFin(),
                poliza.getFechaCancelacion()
        );
    }
}
