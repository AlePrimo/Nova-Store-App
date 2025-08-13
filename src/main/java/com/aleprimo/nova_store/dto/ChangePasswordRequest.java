package com.aleprimo.nova_store.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Petición para cambiar la contraseña de un usuario")
public class ChangePasswordRequest {

    @NotBlank(message = "La contraseña actual es obligatoria")
    @Schema(description = "Contraseña actual del usuario", example = "password123")
    private String currentPassword;

    @NotBlank(message = "La nueva contraseña es obligatoria")
    @Schema(description = "Nueva contraseña que reemplazará a la actual", example = "nuevaPassword456")
    private String newPassword;
}
