package com.aleprimo.nova_store.persistence.implementations;


import com.aleprimo.nova_store.models.Wishlist;
import com.aleprimo.nova_store.persistence.WishlistDAO;
import com.aleprimo.nova_store.repository.WishlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class WishlistDAOImpl implements WishlistDAO {

    private final WishlistRepository wishlistRepository;

    @Override
    public Wishlist save(Wishlist wishlist) {
        return wishlistRepository.save(wishlist);
    }

    @Override
    public Optional<Wishlist> findById(Long id) {
        return wishlistRepository.findById(id);
    }

    @Override
    public Page<Wishlist> findAll(Pageable pageable) {
        return wishlistRepository.findAll(pageable);
    }

    @Override
    public void deleteById(Long id) {
        wishlistRepository.deleteById(id);
    }
}
