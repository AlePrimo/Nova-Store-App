package com.aleprimo.nova_store.handler.exceptions;

import com.aleprimo.nova_store.models.RoleName;

public class RoleNotFoundException extends RuntimeException {
    public RoleNotFoundException(String message) {
        super(message);
    }

    public RoleNotFoundException(RoleName name) {
        super("Rol no encontrado con nombre: " + name.name());
    }
}
