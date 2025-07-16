package com.aleprimo.nova_store.persistence;

import com.aleprimo.nova_store.models.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CustomerDAO {
    Page<Customer> findAll(Pageable pageable);
    Page<Customer> findActive(Pageable pageable);
    Optional<Customer> findById(Long id);
    Optional<Customer> findByEmail(String email);
    Customer save(Customer customer);
    void deleteById(Long id);
}
