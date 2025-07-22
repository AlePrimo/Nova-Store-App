package com.aleprimo.nova_store.repository;

import com.aleprimo.nova_store.models.ShoppingCartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShoppingCartItemRepository extends JpaRepository<ShoppingCartItem, Long> {

    List<ShoppingCartItem> findByShoppingCartId(Long cartId);

    void deleteByShoppingCartId(Long cartId);
}
