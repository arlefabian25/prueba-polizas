package com.prueba.polizas.controller;

import com.prueba.polizas.dto.PolizaResponse;
import com.prueba.polizas.dto.RiesgoRequest;
import com.prueba.polizas.dto.RiesgoResponse;
import com.prueba.polizas.entity.enums.EstadoPoliza;
import com.prueba.polizas.entity.enums.TipoPoliza;
import com.prueba.polizas.service.PolizaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/polizas")
@RequiredArgsConstructor
public class PolizaController {

    private final PolizaService polizaService;

    /** 1. Lista pólizas filtrando por tipo y estado (ambos filtros son opcionales). */
    @GetMapping
    public ResponseEntity<List<PolizaResponse>> listar(@RequestParam(required = false) TipoPoliza tipo,
                                                       @RequestParam(required = false) EstadoPoliza estado) {
        return ResponseEntity.ok(polizaService.listar(tipo, estado));
    }

    /** 2. Riesgos de una póliza. */
    @GetMapping("/{id}/riesgos")
    public ResponseEntity<List<RiesgoResponse>> listarRiesgos(@PathVariable Long id) {
        return ResponseEntity.ok(polizaService.listarRiesgos(id));
    }

    /** 3. Renueva la póliza: incrementa canon y prima en el IPC y la deja en estado RENOVADA. */
    @PostMapping("/{id}/renovar")
    public ResponseEntity<PolizaResponse> renovar(@PathVariable Long id) {
        return ResponseEntity.ok(polizaService.renovar(id));
    }

    /** 4. Cancela la póliza y todos sus riesgos. */
    @PostMapping("/{id}/cancelar")
    public ResponseEntity<PolizaResponse> cancelar(@PathVariable Long id) {
        return ResponseEntity.ok(polizaService.cancelar(id));
    }

    /** 5. Agrega un riesgo. Solo para pólizas colectivas. */
    @PostMapping("/{id}/riesgos")
    public ResponseEntity<RiesgoResponse> agregarRiesgo(@PathVariable Long id,
                                                        @Valid @RequestBody RiesgoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(polizaService.agregarRiesgo(id, request));
    }
}
