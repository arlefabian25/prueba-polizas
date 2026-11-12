package com.prueba.polizas.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * Envía el evento al CORE solo cuando la transacción local se confirmó.
 * Si la operación falla y se hace rollback, el CORE no se entera de un cambio que no ocurrió.
 */
@Component
@RequiredArgsConstructor
public class CoreEventListener {

    private final CoreClient coreClient;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onCoreEvent(CoreEvent event) {
        coreClient.enviar(event);
    }
}
