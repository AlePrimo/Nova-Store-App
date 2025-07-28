package com.aleprimo.nova_store.persistence.implementations;


import com.aleprimo.nova_store.controller.mappers.ReviewMapper;
import com.aleprimo.nova_store.dto.review.ReviewResponseDTO;
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
    private final ReviewMapper reviewMapper;

    @Override
    public ReviewResponseDTO save(ReviewResponseDTO dto) {
        Review review = reviewMapper.toEntity(dto);
        return reviewMapper.toDTO(reviewRepository.save(review));
    }

    @Override
    public Page<ReviewResponseDTO> findAll(Pageable pageable) {
        return reviewRepository.findAll(pageable).map(reviewMapper::toDTO);
    }

    @Override
    public Optional<ReviewResponseDTO> findById(Long id) {
        return reviewRepository.findById(id).map(reviewMapper::toDTO);
    }

    @Override
    public ReviewResponseDTO update(Long id, ReviewResponseDTO dto) {
        Review existing = reviewRepository.findById(id).orElseThrow();
        existing.setRating(dto.rating());
        existing.setComment(dto.comment());
        return reviewMapper.toDTO(reviewRepository.save(existing));
    }

    @Override
    public void deleteById(Long id) {
        reviewRepository.deleteById(id);
    }
}
