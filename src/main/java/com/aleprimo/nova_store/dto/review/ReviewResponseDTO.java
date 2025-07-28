package com.aleprimo.nova_store.dto.review;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponseDTO {

    @Schema(description = "ID único de la review", example = "1")
    private Long id;

    @Schema(description = "Puntaje del review", example = "5")
    private Integer rating;

    @Schema(description = "Comentario del review", example = "Excelente servicio")
    private String comment;

    @Schema(description = "Fecha y hora de creación", example = "2025-07-26T16:34:12")
    private LocalDateTime createdAt;

    @Schema(description = "ID del producto reseñado", example = "3")
    private Long productId;

    @Schema(description = "ID del cliente que realizó la review", example = "8")
    private Long customerId;
}