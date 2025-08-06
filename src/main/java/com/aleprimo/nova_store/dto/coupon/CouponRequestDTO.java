package com.aleprimo.nova_store.dto.coupon;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CouponRequestDTO {

    @NotBlank
    private String code;

    @NotNull
    @Min(0)
    @Max(100)
    private Double discountPercentage;

    @NotNull
    @Future
    private LocalDateTime expirationDate;

    @NotNull
    private Boolean isActive;


}