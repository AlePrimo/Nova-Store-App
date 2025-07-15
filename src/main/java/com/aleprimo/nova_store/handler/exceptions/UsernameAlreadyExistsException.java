package com.aleprimo.nova_store.handler.exceptions;

public class UsernameAlreadyExistsException extends RuntimeException {
    public UsernameAlreadyExistsException(String username) {
        super("El nombre de usuario ya existe: " + username);
    }
}
