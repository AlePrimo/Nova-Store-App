package com.aleprimo.nova_store.dto.order;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "DTO para crear o actualizar una orden")
public class OrderRequestDTO {

    @NotNull(message = "El ID del cliente es obligatorio")
    @Schema(description = "ID del cliente que realiza la orden", example = "3")
    private Long customerId;

    @NotNull(message = "El total es obligatorio")
    @Schema(description = "Total de la orden", example = "150000.00")
    private BigDecimal total;

    @Schema(description = "Estado de la orden", example = "PENDING")
    private String status;
}
