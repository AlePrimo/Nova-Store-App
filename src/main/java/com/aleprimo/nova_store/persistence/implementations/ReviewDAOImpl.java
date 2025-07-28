package com.aleprimo.nova_store.persistence.implementations;

import com.aleprimo.nova_store.models.Review;
import com.aleprimo.nova_store.persistence.ReviewDAO;
import com.aleprimo.nova_store.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ReviewDAOImpl implements ReviewDAO {

    private final ReviewRepository reviewRepository;

    @Override
    public Review save(Review review) {
        return reviewRepository.save(review);
    }

    @Override
    public Page<Review> findAll(Pageable pageable) {
        return reviewRepository.findAll(pageable);
    }

    @Override
    public Optional<Review> findById(Long id) {
        return reviewRepository.findById(id);
    }

    @Override
    public Review update(Long id, Review updatedReview) {
        Review existing = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review no encontrado con ID: " + id));

        existing.setRating(updatedReview.getRating());
        existing.setComment(updatedReview.getComment());

        return reviewRepository.save(existing);
    }

    @Override
    public void deleteById(Long id) {
        reviewRepository.deleteById(id);
    }
}