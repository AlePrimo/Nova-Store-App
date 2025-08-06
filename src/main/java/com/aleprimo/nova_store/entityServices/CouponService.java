package com.aleprimo.nova_store.entityServices;

import com.aleprimo.nova_store.dto.coupon.CouponRequestDTO;
import com.aleprimo.nova_store.dto.coupon.CouponResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CouponService {
    CouponResponseDTO create(CouponRequestDTO dto);
    CouponResponseDTO getById(Long id);
    CouponResponseDTO getByCode(String code);
    void delete(Long id);
    Page<CouponResponseDTO> getAll(Pageable pageable);
}