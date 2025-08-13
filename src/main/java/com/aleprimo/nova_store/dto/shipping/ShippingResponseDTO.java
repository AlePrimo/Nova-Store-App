package com.aleprimo.nova_store.dto.shipping;


import com.aleprimo.nova_store.models.enums.ShippingStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Respuesta con información de un envío")
public class ShippingResponseDTO {


    @Schema(description = "ID único del envío", example = "12")
    private Long id;

    @Schema(description = "ID de la orden asociada", example = "101")
    private Long orderId;

    @Schema(description = "Dirección de envío", example = "Av. Siempre Viva 742")
    private String address;

    @Schema(description = "Ciudad de envío", example = "Springfield")
    private String city;

    @Schema(description = "Código postal", example = "12345")
    private String postalCode;

    @Schema(description = "País de envío", example = "USA")
    private String country;

    @Schema(description = "Estado actual del envío", example = "SHIPPED")
    private ShippingStatus status;

    @Schema(description = "Fecha de envío", example = "2025-08-15T10:00:00")
    private String shippedAt;

    @Schema(description = "Fecha de entrega", example = "2025-08-17T16:00:00")
    private String deliveredAt;
}
