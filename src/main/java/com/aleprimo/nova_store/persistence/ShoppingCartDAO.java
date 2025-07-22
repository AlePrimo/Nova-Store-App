package com.aleprimo.nova_store.persistence;

import com.aleprimo.nova_store.models.ShoppingCart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ShoppingCartDAO {
    Optional<ShoppingCart> findById(Long id);
    Optional<ShoppingCart> findByCustomerId(Long customerId);
    Page<ShoppingCart> findAll(Pageable pageable);
    ShoppingCart save(ShoppingCart shoppingCart);
    void deleteById(Long id);
    boolean existsById(Long id);
}
