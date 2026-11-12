package com.prueba.polizas.client;

/**
 * Evento interno que indica que una póliza cambió de estado y debe informarse al CORE.
 * El servicio de edición del CORE es agnóstico, por eso todos los cambios viajan como ACTUALIZACION.
 */
public record CoreEvent(String evento, Long polizaId) {

    public static final String ACTUALIZACION = "ACTUALIZACION";

    public static CoreEvent actualizacion(Long polizaId) {
        return new CoreEvent(ACTUALIZACION, polizaId);
    }
}
