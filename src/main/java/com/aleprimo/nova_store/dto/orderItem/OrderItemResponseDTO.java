package com.aleprimo.nova_store.dto.orderItem;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "DTO de respuesta con información de un ítem de orden")
public class OrderItemResponseDTO {

    private Long id;

    private Long orderId;

    private Long productId;

    private Integer quantity;

    private BigDecimal unitPrice;
}
