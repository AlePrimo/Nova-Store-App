package com.aleprimo.nova_store.dto.category;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "DTO de respuesta para categoría")
public class CategoryResponseDTO {

    @Schema(description = "ID de la categoría", example = "1")
    Long id;

    @Schema(description = "Nombre de la categoría", example = "Electrónica")
    String name;

    @Schema(description = "Descripción de la categoría", example = "Artículos de electrónica y tecnología")
    String description;

    @Schema(description = "Indica si está activa", example = "true")
    Boolean isActive;

    @Schema(description = "Fecha de creación", example = "2025-07-14T10:30:00")
    LocalDateTime createdAt;

    @Schema(description = "Fecha de última modificación", example = "2025-07-14T12:45:00")
    LocalDateTime updatedAt;
}
