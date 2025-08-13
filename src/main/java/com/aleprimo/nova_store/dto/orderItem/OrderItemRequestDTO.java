package com.aleprimo.nova_store.dto.orderItem;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "DTO para crear o actualizar un ítem de orden")
public class OrderItemRequestDTO {


    @Schema(description = "ID de la orden a la que pertenece el ítem", example = "1")
    private Long orderId;

    @Schema(description = "ID del producto asociado", example = "10")
    @NotNull(message = "El ID del producto es obligatorio")
    private Long productId;

    @Schema(description = "Cantidad del producto", example = "3")
    @NotNull(message = "La cantidad es obligatoria")
    private Integer quantity;
    @Schema(description = "Precio unitario del producto", example = "499.99")
    @NotNull(message = "El precio unitario es obligatorio")
    private BigDecimal unitPrice;
}
