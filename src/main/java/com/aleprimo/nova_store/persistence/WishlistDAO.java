package com.aleprimo.nova_store.persistence;

import com.aleprimo.nova_store.models.Wishlist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface WishlistDAO {
    Wishlist save(Wishlist wishlist);
    Optional<Wishlist> findById(Long id);
    Page<Wishlist> findAll(Pageable pageable);
    void deleteById(Long id);
}
