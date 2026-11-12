package com.prueba.polizas.exception;

/** Una regla de negocio impide la operación (por ejemplo, renovar una póliza cancelada). */
public class BusinessRuleException extends RuntimeException {

    public BusinessRuleException(String message) {
        super(message);
    }
}
