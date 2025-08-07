package com.aleprimo.nova_store.service;

import com.novastore.dao.CouponDAO;
import com.novastore.dto.request.CouponRequestDTO;
import com.novastore.dto.response.CouponResponseDTO;
import com.novastore.entity.Coupon;
import com.novastore.mapper.CouponMapper;
import com.novastore.service.CouponService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class CouponServiceImplTest {

    @Mock
    private CouponDAO couponDAO;

    @Mock
    private CouponMapper couponMapper;

    @InjectMocks
    private CouponServiceImpl couponService;

    private Coupon coupon;
    private CouponRequestDTO requestDTO;
    private CouponResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        coupon = Coupon.builder()
                .id(1L)
                .code("DISCOUNT10")
                .description("10% off")
                .discountPercentage(BigDecimal.valueOf(10))
                .expirationDate(LocalDate.now().plusDays(5))
                .isActive(true)
                .build();

        requestDTO = CouponRequestDTO.builder()
                .code("DISCOUNT10")
                .description("10% off")
                .discountPercentage(BigDecimal.valueOf(10))
                .expirationDate(LocalDate.now().plusDays(5))
                .isActive(true)
                .build();

        responseDTO = CouponResponseDTO.builder()
                .id(1L)
                .code("DISCOUNT10")
                .description("10% off")
                .discountPercentage(BigDecimal.valueOf(10))
                .expirationDate(LocalDate.now().plusDays(5))
                .isActive(true)
                .build();
    }

    @Test
    void testSaveCoupon() {
        when(couponMapper.toEntity(requestDTO)).thenReturn(coupon);
        when(couponDAO.save(coupon)).thenReturn(coupon);
        when(couponMapper.toDTO(coupon)).thenReturn(responseDTO);

        CouponResponseDTO result = couponService.save(requestDTO);

        assertThat(result).isNotNull();
        assertThat(result.getCode()).isEqualTo("DISCOUNT10");
    }

    @Test
    void testFindById() {
        when(couponDAO.findById(1L)).thenReturn(Optional.of(coupon));
        when(couponMapper.toDTO(coupon)).thenReturn(responseDTO);

        CouponResponseDTO result = couponService.findById(1L);
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    void testDelete() {
        couponService.delete(1L);
        verify(couponDAO, times(1)).deleteById(1L);
    }

    @Test
    void testFindAllPaged() {
        Pageable pageable = PageRequest.of(0, 5);
        Page<Coupon> page = new PageImpl<>(java.util.List.of(coupon));
        when(couponDAO.findAll(pageable)).thenReturn(page);
        when(couponMapper.toDTO(any(Coupon.class))).thenReturn(responseDTO);

        Page<CouponResponseDTO> result = couponService.findAll(pageable);
        assertThat(result.getContent()).hasSize(1);
    }
}
