package com.aleprimo.nova_store.dto.payment;


import com.aleprimo.nova_store.models.enums.PaymentMethod;
import com.aleprimo.nova_store.models.enums.PaymentStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Respuesta de un pago realizado en la plataforma")
public class PaymentResponseDTO {
    @Schema(description = "ID único del pago", example = "1")
    private Long id;
    @Schema(description = "ID del pedido asociado al pago", example = "10")
    private Long orderId;
    @Schema(description = "Método de pago utilizado", example = "CREDIT_CARD")
    private PaymentMethod paymentMethod;
    @Schema(description = "Estado actual del pago", example = "COMPLETED")
    private PaymentStatus paymentStatus;
    @Schema(description = "Monto total pagado", example = "1500.00")
    private BigDecimal amount;
    @Schema(description = "Fecha de creación del registro de pago", example = "2025-08-12T14:30:00")
    private LocalDateTime createdAt;
    @Schema(description = "Fecha de última actualización del pago", example = "2025-08-12T15:45:00")
    private LocalDateTime updatedAt;
}
