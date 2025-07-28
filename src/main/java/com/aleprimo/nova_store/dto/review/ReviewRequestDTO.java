
package com.aleprimo.nova_store.dto.review;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRequestDTO {

    @Schema(description = "Puntaje del review (de 1 a 5)", example = "4", required = true)
    @NotNull(message = "El rating no puede ser nulo")
    @Min(value = 1, message = "El rating mínimo es 1")
    @Max(value = 5, message = "El rating máximo es 5")
    private Integer rating;

    @Schema(description = "Comentario del usuario (opcional, hasta 500 caracteres)", example = "Muy buen producto")
    @Size(max = 500, message = "El comentario no puede superar los 500 caracteres")
    private String comment;

    @Schema(description = "ID del producto reseñado", example = "10", required = true)
    @NotNull(message = "El ID del producto es obligatorio")
    private Long productId;

    @Schema(description = "ID del cliente que hizo la reseña", example = "5", required = true)
    @NotNull(message = "El ID del cliente es obligatorio")
    private Long customerId;
}