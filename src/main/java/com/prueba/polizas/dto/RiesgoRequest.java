package com.prueba.polizas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RiesgoRequest(
        @NotBlank(message = "La dirección del inmueble es obligatoria")
        @Size(max = 200, message = "La dirección debe tener máximo 200 caracteres")
        String direccionInmueble,

        @NotBlank(message = "El arrendatario es obligatorio")
        @Size(max = 150, message = "El arrendatario debe tener máximo 150 caracteres")
        String arrendatario,

        @NotBlank(message = "El arrendador es obligatorio")
        @Size(max = 150, message = "El arrendador debe tener máximo 150 caracteres")
        String arrendador
) {
}
