package com.aleprimo.nova_store.dto.adress;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressResponseDTO {

    private Long id;
    private String street;
    private String city;
    private String province;
    private String postalCode;
    private String country;
    private Long customerId;
}
