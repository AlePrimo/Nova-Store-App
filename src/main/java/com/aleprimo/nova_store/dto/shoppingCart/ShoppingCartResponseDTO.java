package com.aleprimo.nova_store.dto.shoppingCart;

import com.aleprimo.nova_store.dto.shoppingCartItem.ShoppingCartItemResponseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO de respuesta de un carrito de compras")
public class ShoppingCartResponseDTO {

    @Schema(description = "ID del carrito", example = "1")
    private Long id;

    @Schema(description = "ID del cliente", example = "1")
    private Long customerId;

    @Schema(description = "Fecha de creación")
    private LocalDateTime createdAt;

    @Schema(description = "Items del carrito")
    private List<ShoppingCartItemResponseDTO> items;
}
