package com.prueba.polizas.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

/** Seguridad mínima: toda petición debe traer el header x-api-key con el valor configurado. */
@Slf4j
@Component
public class ApiKeyFilter extends OncePerRequestFilter {

    public static final String HEADER = "x-api-key";

    private final String apiKey;

    public ApiKeyFilter(@Value("${security.api-key}") String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        // La consola de H2 es solo una herramienta de desarrollo para revisar los datos.
        return request.getRequestURI().startsWith("/h2-console");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        if (apiKey.equals(request.getHeader(HEADER))) {
            chain.doFilter(request, response);
            return;
        }

        log.warn("Petición rechazada sin x-api-key válido: {} {}", request.getMethod(), request.getRequestURI());
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.getWriter().write("""
                {"timestamp":"%s","status":%d,"error":"%s","message":"Header x-api-key ausente o inválido","path":"%s"}"""
                .formatted(LocalDateTime.now(), status.value(), status.getReasonPhrase(), request.getRequestURI()));
    }
}
