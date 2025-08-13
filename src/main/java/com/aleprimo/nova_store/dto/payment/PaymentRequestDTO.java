package com.aleprimo.nova_store.dto.payment;


import com.aleprimo.nova_store.models.enums.PaymentMethod;
import com.aleprimo.nova_store.models.enums.PaymentStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Petición para registrar un nuevo pago")
public class PaymentRequestDTO {

    @NotNull(message = "El ID de la orden es obligatorio")
    @Schema(description = "ID de la orden asociada al pago", example = "10")
    private Long orderId;

    @NotNull(message = "El método de pago es obligatorio")
    @Schema(description = "Método de pago seleccionado", example = "CREDIT_CARD")
    private PaymentMethod paymentMethod;

    @NotNull(message = "El estado del pago es obligatorio")
    @Schema(description = "Estado inicial del pago", example = "PENDING")
    private PaymentStatus paymentStatus;

    @NotNull(message = "El monto es obligatorio")
    @DecimalMin(value = "0.01", message = "El monto debe ser mayor a 0")
    @Schema(description = "Monto total del pago", example = "1999.99")
    private BigDecimal amount;
}
