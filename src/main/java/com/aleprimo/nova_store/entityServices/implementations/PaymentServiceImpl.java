package com.aleprimo.nova_store.entityServices.implementations;

import com.aleprimo.nova_store.dto.payment.PaymentRequestDTO;
import com.aleprimo.nova_store.dto.payment.PaymentResponseDTO;
import com.aleprimo.nova_store.entityServices.PaymentService;

import com.aleprimo.nova_store.persistence.PaymentDAO;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentDAO paymentDAO;

    @Override
    @Transactional
    public PaymentResponseDTO create(PaymentRequestDTO dto) {
        return paymentDAO.create(dto);
    }

    @Override
    @Transactional
    public PaymentResponseDTO update(Long id, PaymentRequestDTO dto) {
        return paymentDAO.update(id, dto);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        paymentDAO.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentResponseDTO getById(Long id) {
        return paymentDAO.getById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PaymentResponseDTO> getAll(Pageable pageable) {
        return paymentDAO.getAll(pageable);
    }
}
