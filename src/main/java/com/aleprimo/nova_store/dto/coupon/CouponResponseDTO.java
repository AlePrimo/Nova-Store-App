package com.aleprimo.nova_store.dto.coupon;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CouponResponseDTO {

    private Long id;
    private String code;
    private Double discountPercentage;
    private LocalDateTime expirationDate;
    private Boolean isActive;
}