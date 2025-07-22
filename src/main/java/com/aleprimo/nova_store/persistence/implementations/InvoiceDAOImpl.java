package com.aleprimo.nova_store.persistence.implementations;

import com.aleprimo.nova_store.models.Invoice;
import com.aleprimo.nova_store.persistence.InvoiceDAO;
import com.aleprimo.nova_store.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class InvoiceDAOImpl implements InvoiceDAO {

    private final InvoiceRepository invoiceRepository;

    @Override
    public Optional<Invoice> findById(Long id) {
        return invoiceRepository.findById(id);
    }

    @Override
    public Optional<Invoice> findByOrderId(Long orderId) {
        return invoiceRepository.findByOrderId(orderId);
    }

    @Override
    public Page<Invoice> findAll(Pageable pageable) {
        return invoiceRepository.findAll(pageable);
    }

    @Override
    public Invoice save(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    @Override
    public void deleteById(Long id) {
        invoiceRepository.deleteById(id);
    }
}
