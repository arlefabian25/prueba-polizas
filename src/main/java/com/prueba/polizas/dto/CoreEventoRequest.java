package com.prueba.polizas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/** Contrato del servicio de edición del CORE: {"evento": "ACTUALIZACION", "polizaId": 555}. */
public record CoreEventoRequest(
        @NotBlank(message = "El evento es obligatorio")
        String evento,

        @NotNull(message = "El polizaId es obligatorio")
        Long polizaId
) {
}
