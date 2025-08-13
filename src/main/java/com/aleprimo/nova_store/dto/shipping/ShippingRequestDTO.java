package com.aleprimo.nova_store.dto.shipping;


import com.aleprimo.nova_store.models.enums.ShippingStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Petición para registrar un nuevo envío")
public class ShippingRequestDTO {

    @Schema(description = "ID de la orden asociada al envío", example = "55")
    @NotNull(message = "orderId es obligatorio")
    private Long orderId;

    @Schema(description = "Dirección exacta de envío", example = "Calle Falsa 123")
    @NotBlank(message = "address es obligatorio")
    private String address;

    @Schema(description = "Ciudad de destino del envío", example = "Buenos Aires")
    @NotBlank(message = "city es obligatorio")
    private String city;

    @Schema(description = "Código postal del destino", example = "1000")
    @NotBlank(message = "postalCode es obligatorio")
    private String postalCode;

    @Schema(description = "País de destino del envío", example = "Argentina")
    @NotBlank(message = "country es obligatorio")
    private String country;
    @Schema(description = "Estado actual del envío", example = "PENDING")
    private ShippingStatus status;

}
