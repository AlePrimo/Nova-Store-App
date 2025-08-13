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

    @Schema(description = "ID único del ítem", example = "1")
    private Long id;

    @Schema(description = "ID de la orden asociada", example = "1")
    private Long orderId;

    @Schema(description = "ID del producto asociado", example = "10")
    private Long productId;

    @Schema(description = "Cantidad del producto", example = "3")
    private Integer quantity;
    @Schema(description = "Precio unitario del producto", example = "499.99")
    private BigDecimal unitPrice;
}
