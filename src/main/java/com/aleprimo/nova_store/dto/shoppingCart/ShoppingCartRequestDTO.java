package com.aleprimo.nova_store.dto.shoppingCart;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para crear o actualizar un carrito de compras")
public class ShoppingCartRequestDTO {

    @NotNull
    @Schema(description = "ID del cliente", example = "1")
    private Long customerId;
}
