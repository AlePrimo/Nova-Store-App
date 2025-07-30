package com.aleprimo.nova_store.dto.adress;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressRequestDTO {

    @NotBlank
    private String street;

    @NotBlank
    private String city;

    @NotBlank
    private String province;

    @NotBlank
    private String postalCode;

    @NotBlank
    private String country;

    @NotNull
    private Long customerId;
}
