package com.aleprimo.nova_store.persistence.implementations;


import com.aleprimo.nova_store.models.Coupon;
import com.aleprimo.nova_store.persistence.CouponDAO;
import com.aleprimo.nova_store.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CouponDAOImpl implements CouponDAO {

    private final CouponRepository couponRepository;

    @Override
    public Coupon save(Coupon coupon) {
        return couponRepository.save(coupon);
    }

    @Override
    public Optional<Coupon> findById(Long id) {
        return couponRepository.findById(id);
    }

    @Override
    public Optional<Coupon> findByCode(String code) {
        return couponRepository.findByCode(code);
    }

    @Override
    public void deleteById(Long id) {
        couponRepository.deleteById(id);
    }

    @Override
    public Page<Coupon> findAll(Pageable pageable) {
        return couponRepository.findAll(pageable);
    }
}
