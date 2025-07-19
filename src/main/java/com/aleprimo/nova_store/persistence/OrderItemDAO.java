package com.aleprimo.nova_store.persistence;


import com.aleprimo.nova_store.models.OrderItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface OrderItemDAO {
    OrderItem save(OrderItem orderItem);
    Optional<OrderItem> findById(Long id);
    Page<OrderItem> findAll(Pageable pageable);
    void deleteById(Long id);
}
