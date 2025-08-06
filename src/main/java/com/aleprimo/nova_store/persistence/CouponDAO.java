package com.aleprimo.nova_store.persistence;



import java.util.Optional;

import com.aleprimo.nova_store.models.Coupon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CouponDAO {
    Coupon save(Coupon coupon);
    Optional<Coupon> findById(Long id);
    Optional<Coupon> findByCode(String code);
    void deleteById(Long id);
    Page<Coupon> findAll(Pageable pageable);
}
