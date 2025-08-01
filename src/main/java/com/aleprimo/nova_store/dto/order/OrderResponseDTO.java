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

    private Long id;

    private Long customerId;

    private BigDecimal total;

    private OrderStatus orderStatus;

    private LocalDateTime createdAt;
}
