package com.aleprimo.nova_store.dto.customer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Respuesta con la información de un cliente")
public class CustomerResponseDTO {

    @Schema(description = "ID único del cliente", example = "5")
    private Long id;
    @Schema(description = "Nombre del cliente", example = "Juan")
    private String firstName;
    @Schema(description = "Apellido del cliente", example = "Pérez")
    private String lastName;
    @Schema(description = "Correo electrónico del cliente", example = "juan.perez@email.com")
    private String email;
    @Schema(description = "Estado de actividad del cliente", example = "true")
    private Boolean isActive;
    @Schema(description = "Fecha de creación del cliente", example = "2025-01-10T14:30:00")
    private LocalDateTime createdAt;
    @Schema(description = "Última fecha de actualización", example = "2025-08-10T10:15:00")
    private LocalDateTime updatedAt;
}
