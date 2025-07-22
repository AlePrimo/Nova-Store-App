package com.aleprimo.nova_store.controller;

import com.aleprimo.nova_store.dto.payment.PaymentRequestDTO;
import com.aleprimo.nova_store.dto.payment.PaymentResponseDTO;
import com.aleprimo.nova_store.entityServices.PaymentService;
import com.aleprimo.nova_store.models.enums.PaymentMethod;
import com.aleprimo.nova_store.models.enums.PaymentStatus;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@WithMockUser(roles = "ADMIN")
@WebMvcTest(PaymentController.class)
@ActiveProfiles("test")
class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentService paymentService;

    @Autowired
    private ObjectMapper objectMapper;

    private PaymentRequestDTO requestDTO;
    private PaymentResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        requestDTO = PaymentRequestDTO.builder()
                .orderId(1L)
                .amount(new BigDecimal("99.99"))
                .paymentMethod(PaymentMethod.CREDIT_CARD)
                .paymentStatus(PaymentStatus.COMPLETED)
                .build();

        responseDTO = PaymentResponseDTO.builder()
                .id(1L)
                .orderId(1L)
                .amount(new BigDecimal("99.99"))
                .paymentMethod(PaymentMethod.CREDIT_CARD)
                .paymentStatus(PaymentStatus.COMPLETED)
                .build();
    }

    @Test
    void shouldCreatePayment() throws Exception {
        when(paymentService.create(any())).thenReturn(responseDTO);

        mockMvc.perform(post("/api/payments")
                        .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(responseDTO.getId()));
    }

    @Test
    void shouldUpdatePayment() throws Exception {
        when(paymentService.update(eq(1L), any())).thenReturn(responseDTO);

        mockMvc.perform(put("/api/payments/1")
                        .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId").value(1L));
    }

    @Test
    void shouldDeletePayment() throws Exception {
        doNothing().when(paymentService).deleteById(1L);

        mockMvc.perform(delete("/api/payments/1").with(csrf()))
                .andExpect(status().isNoContent());
       
    }

    @Test
    void shouldGetById() throws Exception {
        when(paymentService.getById(1L)).thenReturn(responseDTO);

        mockMvc.perform(get("/api/payments/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.paymentMethod").value("CREDIT_CARD"));
    }

    @Test
    void shouldGetAllPayments() throws Exception {
        when(paymentService.getAll(any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(responseDTO)));

        mockMvc.perform(get("/api/payments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(responseDTO.getId()));
    }
}
