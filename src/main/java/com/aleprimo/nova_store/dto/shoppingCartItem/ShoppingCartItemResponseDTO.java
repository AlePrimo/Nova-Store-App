package com.aleprimo.nova_store.dto.shoppingCartItem;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO de respuesta de un ítem del carrito")
public class ShoppingCartItemResponseDTO {

    @Schema(description = "ID del ítem", example = "5")
    private Long id;

    @Schema(description = "ID del producto", example = "10")
    private Long productId;

    @Schema(description = "Nombre del producto")
    private String productName;

    @Schema(description = "Cantidad del producto", example = "2")
    private Integer quantity;

    @Schema(description = "Precio unitario del producto")
    private BigDecimal price;
}
