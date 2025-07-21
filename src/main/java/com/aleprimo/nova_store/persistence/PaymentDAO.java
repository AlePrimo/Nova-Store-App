package com.aleprimo.nova_store.persistence;

import com.aleprimo.nova_store.dto.payment.PaymentRequestDTO;
import com.aleprimo.nova_store.dto.payment.PaymentResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PaymentDAO {
    PaymentResponseDTO create(PaymentRequestDTO dto);
    PaymentResponseDTO update(Long id, PaymentRequestDTO dto);
    void deleteById(Long id);
    PaymentResponseDTO getById(Long id);
    Page<PaymentResponseDTO> getAll(Pageable pageable);
}
