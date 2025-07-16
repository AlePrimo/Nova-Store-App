package com.aleprimo.nova_store.repository;

import com.aleprimo.nova_store.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByEmail(String email);
    List<Customer> findByFirstNameContainingIgnoreCase(String firstName);
    List<Customer> findByLastNameContainingIgnoreCase(String lastName);
}
