package com.prueba.polizas.client;

import com.prueba.polizas.config.ApiKeyFilter;
import com.prueba.polizas.dto.CoreEventoRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

/**
 * Adapter hacia el servicio de edición del CORE (capa media en WebLogic).
 * En esta prueba apunta al mock POST /core-mock/evento.
 */
@Slf4j
@Component
public class CoreClient {

    private final RestClient restClient;
    private final String coreUrl;
    private final String apiKey;

    public CoreClient(@Value("${core.url}") String coreUrl, @Value("${security.api-key}") String apiKey) {
        this.restClient = RestClient.create();
        this.coreUrl = coreUrl;
        this.apiKey = apiKey;
    }

    public void enviar(CoreEvent event) {
        try {
            restClient.post()
                    .uri(coreUrl)
                    .contentType(MediaType.APPLICATION_JSON)
                    .header(ApiKeyFilter.HEADER, apiKey)
                    .body(new CoreEventoRequest(event.evento(), event.polizaId()))
                    .retrieve()
                    .toBodilessEntity();
            log.info("Evento {} de la póliza {} enviado al CORE", event.evento(), event.polizaId());
        } catch (RestClientException e) {
            // La operación local ya se confirmó; el envío queda pendiente de reintento (ver README).
            log.error("No se pudo enviar el evento {} de la póliza {} al CORE: {}",
                    event.evento(), event.polizaId(), e.getMessage());
        }
    }
}
