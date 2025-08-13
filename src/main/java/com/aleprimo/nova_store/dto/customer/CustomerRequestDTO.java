package com.aleprimo.nova_store.dto.customer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Petición para registrar o actualizar un cliente")
public class CustomerRequestDTO {
    @Schema(description = "Nombre del cliente", example = "Juan")
    private String firstName;
    @Schema(description = "Apellido del cliente", example = "Pérez")
    private String lastName;
    @Schema(description = "Correo electrónico del cliente", example = "juan.perez@email.com")
    private String email;
    @Schema(description = "Estado de actividad del cliente", example = "true")
    private Boolean isActive;
}
