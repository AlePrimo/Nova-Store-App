package com.aleprimo.nova_store.entityServices.implementations;

import com.aleprimo.nova_store.controller.mappers.CouponMapper;
import com.aleprimo.nova_store.dto.coupon.CouponRequestDTO;
import com.aleprimo.nova_store.dto.coupon.CouponResponseDTO;
import com.aleprimo.nova_store.entityServices.CouponService;
import com.aleprimo.nova_store.models.Coupon;
import com.aleprimo.nova_store.persistence.CouponDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {

    private final CouponDAO couponDAO;

    @Override
    public CouponResponseDTO create(CouponRequestDTO dto) {
        Coupon saved = couponDAO.save(CouponMapper.toEntity(dto));
        return CouponMapper.toDTO(saved);
    }

    @Override
    public CouponResponseDTO getById(Long id) {
        return couponDAO.findById(id)
                .map(CouponMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Coupon not found"));
    }

    @Override
    public CouponResponseDTO getByCode(String code) {
        return couponDAO.findByCode(code)
                .map(CouponMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Coupon not found"));
    }

    @Override
    public void delete(Long id) {
        couponDAO.deleteById(id);
    }

    @Override
    public Page<CouponResponseDTO> getAll(Pageable pageable) {
        return couponDAO.findAll(pageable).map(CouponMapper::toDTO);
    }
}