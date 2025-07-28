package com.aleprimo.nova_store.dto.review;

import java.time.LocalDateTime;

public record ReviewResponseDTO(
    Long id,
    Integer rating,
    String comment,
    LocalDateTime createdAt,
    Long productId,
    Long customerId
) {}
