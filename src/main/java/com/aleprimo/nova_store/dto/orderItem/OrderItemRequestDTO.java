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

    @NotNull(message = "El ID del producto es obligatorio")
    private Long productId;

    @NotNull(message = "La cantidad es obligatoria")
    private Integer quantity;

    @NotNull(message = "El precio unitario es obligatorio")
    private BigDecimal unitPrice;
}
