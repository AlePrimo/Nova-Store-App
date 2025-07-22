package com.aleprimo.nova_store.persistence.implementations;

import com.aleprimo.nova_store.models.ShoppingCart;
import com.aleprimo.nova_store.persistence.ShoppingCartDAO;
import com.aleprimo.nova_store.repository.ShoppingCartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ShoppingCartDAOImpl implements ShoppingCartDAO {

    private final ShoppingCartRepository shoppingCartRepository;

    @Override
    public Optional<ShoppingCart> findById(Long id) {
        return shoppingCartRepository.findById(id);
    }

    @Override
    public Optional<ShoppingCart> findByCustomerId(Long customerId) {
        return shoppingCartRepository.findByCustomerId(customerId);
    }

    @Override
    public Page<ShoppingCart> findAll(Pageable pageable) {
        return shoppingCartRepository.findAll(pageable);
    }

    @Override
    public ShoppingCart save(ShoppingCart shoppingCart) {
        return shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public void deleteById(Long id) {
        shoppingCartRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
       return shoppingCartRepository.existsById(id);
    }
}
