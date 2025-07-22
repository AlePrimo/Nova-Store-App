package com.aleprimo.nova_store.dto.shoppingCartItem;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para agregar un item al carrito")
public class ShoppingCartItemRequestDTO {

    @NotNull
    @Schema(description = "ID del carrito", example = "1")
    private Long shoppingCartId;

    @NotNull
    @Schema(description = "ID del producto", example = "10")
    private Long productId;

    @Min(1)
    @Schema(description = "Cantidad de producto", example = "2")
    private Integer quantity;
}
