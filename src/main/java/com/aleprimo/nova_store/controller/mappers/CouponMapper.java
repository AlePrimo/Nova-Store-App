package com.aleprimo.nova_store.controller.mappers;

import com.aleprimo.nova_store.dto.coupon.CouponRequestDTO;
import com.aleprimo.nova_store.dto.coupon.CouponResponseDTO;
import com.aleprimo.nova_store.models.Coupon;

public class CouponMapper {

    public  Coupon toEntity(CouponRequestDTO dto) {
        return Coupon.builder()
                .code(dto.getCode())
                .discountPercentage(dto.getDiscountPercentage())
                .expirationDate(dto.getExpirationDate())
                .isActive(dto.getIsActive())
                .build();
    }

    public  CouponResponseDTO toDTO(Coupon coupon) {
        return CouponResponseDTO.builder()
                .id(coupon.getId())
                .code(coupon.getCode())
                .discountPercentage(coupon.getDiscountPercentage())
                .expirationDate(coupon.getExpirationDate())
                .isActive(coupon.getIsActive())
                .build();
    }
}