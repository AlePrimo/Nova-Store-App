package com.aleprimo.nova_store.persistence.implementations;

import com.aleprimo.nova_store.models.Customer;
import com.aleprimo.nova_store.persistence.CustomerDAO;
import com.aleprimo.nova_store.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CustomerDAOImpl implements CustomerDAO {

    private final CustomerRepository repository;

    @Override
    public Page<Customer> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Page<Customer> findActive(Pageable pageable) {
        return (Page<Customer>) repository.findAll(pageable)
                .map(c -> c.getIsActive() ? c : null)
                .filter(c -> c != null);
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public Customer save(Customer customer) {
        return repository.save(customer);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
