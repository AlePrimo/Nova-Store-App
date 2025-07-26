package com.aleprimo.nova_store.dto.shipping;


import com.aleprimo.nova_store.models.enums.ShippingStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShippingRequestDTO {

    @NotNull(message = "orderId es obligatorio")
    private Long orderId;

    @NotBlank(message = "address es obligatorio")
    private String address;

    @NotBlank(message = "city es obligatorio")
    private String city;

    @NotBlank(message = "postalCode es obligatorio")
    private String postalCode;

    @NotBlank(message = "country es obligatorio")
    private String country;

    private ShippingStatus status;

}
