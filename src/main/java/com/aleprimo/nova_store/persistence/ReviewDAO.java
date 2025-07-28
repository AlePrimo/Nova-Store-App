package com.aleprimo.nova_store.persistence;

import com.aleprimo.nova_store.models.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ReviewDAO {
    Review save(Review review);
    Page<Review> findAll(Pageable pageable);
    Optional<Review> findById(Long id);
    Review update(Long id, Review updatedReview);
    void deleteById(Long id);
}
