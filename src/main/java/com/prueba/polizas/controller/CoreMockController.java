package com.prueba.polizas.controller;

import com.prueba.polizas.dto.CoreEventoRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/** Mock del servicio de edición del CORE: solo registra en logs que la operación se intentó enviar. */
@Slf4j
@RestController
@RequestMapping("/core-mock")
public class CoreMockController {

    @PostMapping("/evento")
    public ResponseEntity<Map<String, String>> recibirEvento(@Valid @RequestBody CoreEventoRequest request) {
        log.info("[CORE-MOCK] Operación enviada al CORE: evento={}, polizaId={}", request.evento(), request.polizaId());
        return ResponseEntity.accepted().body(Map.of("mensaje", "Evento registrado en el CORE (mock)"));
    }
}
