package com.aleprimo.nova_store.dto.invoice;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para crear una factura")
public class InvoiceRequestDTO {

    @NotNull
    @Schema(description = "ID de la orden relacionada", example = "1")
    private Long orderId;
}
