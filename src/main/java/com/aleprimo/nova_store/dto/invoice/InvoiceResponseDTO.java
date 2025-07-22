package com.aleprimo.nova_store.dto.invoice;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO de respuesta de una factura")
public class InvoiceResponseDTO {

    @Schema(description = "ID de la factura", example = "1")
    private Long id;

    @Schema(description = "ID de la orden", example = "1")
    private Long orderId;

    @Schema(description = "Monto total")
    private BigDecimal totalAmount;

    @Schema(description = "Fecha de creación")
    private LocalDateTime createdAt;
}
