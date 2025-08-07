package com.aleprimo.nova_store.repository;


import com.aleprimo.nova_store.models.Coupon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class CouponRepositoryTest {

    @Autowired
    private CouponRepository couponRepository;

    private Coupon coupon;

    @BeforeEach
    void setUp() {
        coupon = Coupon.builder()
                .code("DISCOUNT10")

                .discountPercentage(10.0)
                .expirationDate(LocalDateTime.now().plusDays(10))
                .isActive(true)
                .build();
        couponRepository.save(coupon);
    }

    @Test
    void testFindByCode() {
        Optional<Coupon> found = couponRepository.findByCode("DISCOUNT10");
        assertThat(found).isPresent();
        assertThat(found.get().getDiscountPercentage()).isEqualTo(10.0);
    }

    @Test
    void testSaveCoupon() {
        Coupon newCoupon = Coupon.builder()
                .code("WINTER20")

                .discountPercentage(20.0)
                .expirationDate(LocalDateTime.now().plusDays(20))
                .isActive(true)
                .build();

        Coupon saved = couponRepository.save(newCoupon);
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getCode()).isEqualTo("WINTER20");
    }

    @Test
    void testDeleteCoupon() {
        couponRepository.delete(coupon);
        Optional<Coupon> deleted = couponRepository.findByCode("DISCOUNT10");
        assertThat(deleted).isNotPresent();
    }
}
