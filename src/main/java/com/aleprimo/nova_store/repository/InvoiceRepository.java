package com.aleprimo.nova_store.repository;

import com.aleprimo.nova_store.models.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    Optional<Invoice> findByOrderId(Long orderId);

    boolean existsByOrderId(Long orderId);
}
