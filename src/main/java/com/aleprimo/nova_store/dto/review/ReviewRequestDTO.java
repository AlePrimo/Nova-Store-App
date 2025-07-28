package com.aleprimo.nova_store.dto.review;

import jakarta.validation.constraints.*;

public record ReviewRequestDTO(

    @NotNull(message = "El rating no puede ser nulo")
    @Min(value = 1, message = "El rating mínimo es 1")
    @Max(value = 5, message = "El rating máximo es 5")
    Integer rating,

    @Size(max = 500, message = "El comentario no puede superar los 500 caracteres")
    String comment,

    @NotNull(message = "El ID del producto es obligatorio")
    Long productId,

    @NotNull(message = "El ID del cliente es obligatorio")
    Long customerId
) {}
