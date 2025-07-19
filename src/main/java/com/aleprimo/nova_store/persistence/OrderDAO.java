package com.aleprimo.nova_store.persistence;


import com.aleprimo.nova_store.models.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface OrderDAO {
    Order save(Order order);
    Optional<Order> findById(Long id);
    Page<Order> findAll(Pageable pageable);
    void deleteById(Long id);
}
