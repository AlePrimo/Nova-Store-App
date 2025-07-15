package com.aleprimo.nova_store.handler.exceptions;

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String email) {
        super("El email ya está en uso: " + email);
    }
}
