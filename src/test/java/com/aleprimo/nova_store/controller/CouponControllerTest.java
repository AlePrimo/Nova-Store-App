package com.aleprimo.nova_store.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.novastore.dto.request.CouponRequestDTO;
import com.novastore.dto.response.CouponResponseDTO;
import com.novastore.service.CouponService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CouponController.class)
@ActiveProfiles("test")
class CouponControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CouponService couponService;

    @Autowired
    private ObjectMapper objectMapper;

    private CouponRequestDTO requestDTO;
    private CouponResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
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
    void testSaveCoupon() throws Exception {
        Mockito.when(couponService.save(any(CouponRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/api/coupons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code").value("DISCOUNT10"));
    }

    @Test
    void testGetCouponById() throws Exception {
        Mockito.when(couponService.findById(1L)).thenReturn(responseDTO);

        mockMvc.perform(get("/api/coupons/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testDeleteCoupon() throws Exception {
        mockMvc.perform(delete("/api/coupons/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testGetAllCouponsPaged() throws Exception {
        Page<CouponResponseDTO> page = new PageImpl<>(List.of(responseDTO));
        Mockito.when(couponService.findAll(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/api/coupons"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(1));
    }
}
