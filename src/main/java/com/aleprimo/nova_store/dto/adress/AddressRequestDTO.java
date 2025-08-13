package com.aleprimo.nova_store.dto.adress;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Petición para crear o actualizar una dirección")
public class AddressRequestDTO {
    @Schema(description = "Calle y número", example = "Av. Siempre Viva 742")
    @NotBlank
    private String street;
    @Schema(description = "Ciudad", example = "Springfield")
    @NotBlank
    private String city;
    @Schema(description = "Provincia o estado", example = "Buenos Aires")
    @NotBlank
    private String province;
    @Schema(description = "Código postal", example = "1234")
    @NotBlank
    private String postalCode;
    @Schema(description = "País", example = "Argentina")
    @NotBlank
    private String country;
    @Schema(description = "ID del cliente asociado", example = "5")
    @NotNull
    private Long customerId;
}
