package com.aleprimo.nova_store.repository;

import com.aleprimo.nova_store.models.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {

    Optional<ShoppingCart> findByCustomerId(Long customerId);

    boolean existsByCustomerId(Long customerId);
}
