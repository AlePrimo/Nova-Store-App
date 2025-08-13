package com.aleprimo.nova_store.dto.purchaseHistory;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
@Schema(description = "Detalle de un ítem dentro de un pedido o historial de compra")
public class OrderItemDTO {
    @Schema(description = "Nombre del producto", example = "Laptop Gamer Asus")
    private String productName;
    @Schema(description = "Cantidad de unidades compradas", example = "2")
    private int quantity;
    @Schema(description = "Precio unitario del producto", example = "750.00")
    private BigDecimal price;
}
