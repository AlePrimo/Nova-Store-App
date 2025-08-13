package com.aleprimo.nova_store.dto.order;

import com.aleprimo.nova_store.models.enums.OrderStatus;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "DTO de respuesta con información de una orden")
public class OrderResponseDTO {
    @Schema(description = "ID único de la orden", example = "1")
    private Long id;
    @Schema(description = "ID del cliente que realizó la orden", example = "5")
    private Long customerId;
    @Schema(description = "Monto total de la orden", example = "2500.50")
    private BigDecimal total;

    @Schema(description = "Estado actual de la orden", example = "SHIPPED")
    private OrderStatus orderStatus;
    @Schema(description = "Fecha de creación de la orden", example = "2025-08-12T15:30:00")
    private LocalDateTime createdAt;
}
