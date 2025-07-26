package com.aleprimo.nova_store.dto.shipping;


import com.aleprimo.nova_store.models.enums.ShippingStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShippingResponseDTO {

    private Long id;
    private Long orderId;
    private String address;
    private String city;
    private String postalCode;
    private String country;
    private ShippingStatus status;
    private String shippedAt;
    private String deliveredAt;
}
