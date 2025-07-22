package com.aleprimo.nova_store.service;

import com.aleprimo.nova_store.dto.payment.PaymentRequestDTO;
import com.aleprimo.nova_store.dto.payment.PaymentResponseDTO;
import com.aleprimo.nova_store.entityServices.implementations.PaymentServiceImpl;
import com.aleprimo.nova_store.models.enums.PaymentMethod;
import com.aleprimo.nova_store.models.enums.PaymentStatus;
import com.aleprimo.nova_store.persistence.PaymentDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class PaymentServiceImplTest {

    private PaymentDAO paymentDAO;
    private PaymentServiceImpl paymentService;

    private PaymentRequestDTO requestDTO;
    private PaymentResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        paymentDAO = mock(PaymentDAO.class);
        paymentService = new PaymentServiceImpl(paymentDAO);

        requestDTO = PaymentRequestDTO.builder()
                .orderId(1L)
                .amount(new BigDecimal("120.00"))
                .paymentMethod(PaymentMethod.PAYPAL)
                .paymentStatus(PaymentStatus.PENDING)
                .build();

        responseDTO = PaymentResponseDTO.builder()
                .id(1L)
                .orderId(1L)
                .amount(new BigDecimal("120.00"))
                .paymentMethod(PaymentMethod.PAYPAL)
                .paymentStatus(PaymentStatus.PENDING)
                .build();
    }

    @Test
    void shouldCreatePayment() {
        when(paymentDAO.create(requestDTO)).thenReturn(responseDTO);
        PaymentResponseDTO result = paymentService.create(requestDTO);
        assertThat(result).isEqualTo(responseDTO);
    }

    @Test
    void shouldUpdatePayment() {
        when(paymentDAO.update(eq(1L), any(PaymentRequestDTO.class))).thenReturn(responseDTO);
        PaymentResponseDTO result = paymentService.update(1L, requestDTO);
        assertThat(result).isEqualTo(responseDTO);
    }

    @Test
    void shouldDeleteById() {
        doNothing().when(paymentDAO).deleteById(1L);
        paymentService.deleteById(1L);
        verify(paymentDAO, times(1)).deleteById(1L);
    }

    @Test
    void shouldGetById() {
        when(paymentDAO.getById(1L)).thenReturn(responseDTO);
        PaymentResponseDTO result = paymentService.getById(1L);
        assertThat(result).isEqualTo(responseDTO);
    }

    @Test
    void shouldGetAllPayments() {
        Page<PaymentResponseDTO> page = new PageImpl<>(List.of(responseDTO));
        when(paymentDAO.getAll(any(Pageable.class))).thenReturn(page);
        Page<PaymentResponseDTO> result = paymentService.getAll(Pageable.unpaged());
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0)).isEqualTo(responseDTO);
    }
}
