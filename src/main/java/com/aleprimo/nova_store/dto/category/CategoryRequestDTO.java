package com.aleprimo.nova_store.dto.category;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "DTO para crear/actualizar una categoría")
public class CategoryRequestDTO {

    @NotBlank(message = "El nombre es obligatorio")
    @Schema(description = "Nombre de la categoría", example = "Electrónica")
    String name;

    @NotBlank(message = "La descripción es obligatoria")
    @Schema(description = "Descripción de la categoría", example = "Artículos de electrónica y tecnología")
    String description;

    @Schema(description = "Estado activo de la categoría", example = "true")
    Boolean isActive;
}
