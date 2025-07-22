package com.aleprimo.nova_store.persistence;

import com.aleprimo.nova_store.models.Invoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface InvoiceDAO {
    Optional<Invoice> findById(Long id);
    Optional<Invoice> findByOrderId(Long orderId);
    Page<Invoice> findAll(Pageable pageable);
    Invoice save(Invoice invoice);
    void deleteById(Long id);
    boolean existById(Long id);
}
