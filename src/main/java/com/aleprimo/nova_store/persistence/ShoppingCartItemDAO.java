package com.aleprimo.nova_store.persistence;

import com.aleprimo.nova_store.models.ShoppingCartItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ShoppingCartItemDAO {
    Optional<ShoppingCartItem> findById(Long id);
    List<ShoppingCartItem> findByShoppingCartId(Long cartId);
    Page<ShoppingCartItem> findAll(Pageable pageable);
    ShoppingCartItem save(ShoppingCartItem item);
    void deleteById(Long id);
    void deleteByShoppingCartId(Long cartId);
    boolean existsById(Long id);
}
