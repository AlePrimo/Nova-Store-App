package com.aleprimo.nova_store.dto.adress;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Respuesta con información de una dirección")
public class AddressResponseDTO {
    @Schema(description = "ID único de la dirección", example = "1")
    private Long id;
    @Schema(description = "Calle y número", example = "Av. Siempre Viva 742")
    private String street;
    @Schema(description = "Ciudad", example = "Springfield")
    private String city;

    @Schema(description = "Provincia o estado", example = "Buenos Aires")
    private String province;
    @Schema(description = "Código postal", example = "1234")
    private String postalCode;
    @Schema(description = "País", example = "Argentina")
    private String country;
    @Schema(description = "ID del cliente asociado", example = "5")
    private Long customerId;
}
