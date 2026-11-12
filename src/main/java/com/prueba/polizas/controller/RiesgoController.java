package com.prueba.polizas.controller;

import com.prueba.polizas.dto.RiesgoResponse;
import com.prueba.polizas.service.RiesgoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/riesgos")
@RequiredArgsConstructor
public class RiesgoController {

    private final RiesgoService riesgoService;

    /** 6. Cancela un riesgo de una póliza colectiva. */
    @PostMapping("/{id}/cancelar")
    public ResponseEntity<RiesgoResponse> cancelar(@PathVariable Long id) {
        return ResponseEntity.ok(riesgoService.cancelar(id));
    }
}
