package com.aleprimo.nova_store.models.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Nombre del rol de usuario")
public enum RoleName {
    ROLE_USER,
    ROLE_ADMIN,
    ROLE_TEST
}
