package com.aleprimo.nova_store.persistence.implementations;

import com.aleprimo.nova_store.models.ShoppingCartItem;
import com.aleprimo.nova_store.persistence.ShoppingCartItemDAO;
import com.aleprimo.nova_store.repository.ShoppingCartItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ShoppingCartItemDAOImpl implements ShoppingCartItemDAO {

    private final ShoppingCartItemRepository itemRepository;

    @Override
    public Optional<ShoppingCartItem> findById(Long id) {
        return itemRepository.findById(id);
    }

    @Override
    public List<ShoppingCartItem> findByShoppingCartId(Long cartId) {
        return itemRepository.findByShoppingCartId(cartId);
    }

    @Override
    public Page<ShoppingCartItem> findAll(Pageable pageable) {
        return itemRepository.findAll(pageable);
    }

    @Override
    public ShoppingCartItem save(ShoppingCartItem item) {
        return itemRepository.save(item);
    }

    @Override
    public void deleteById(Long id) {
        itemRepository.deleteById(id);
    }

    @Override
    public void deleteByShoppingCartId(Long cartId) {
        itemRepository.deleteByShoppingCartId(cartId);
    }
}
