package com.aleprimo.nova_store.entityServices;

import com.aleprimo.nova_store.dto.review.ReviewRequestDTO;
import com.aleprimo.nova_store.dto.review.ReviewResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewService {
    ReviewResponseDTO createReview(ReviewRequestDTO dto);
    ReviewResponseDTO getReviewById(Long id);
    Page<ReviewResponseDTO> getAllReviews(Pageable pageable);
    ReviewResponseDTO updateReview(Long id, ReviewRequestDTO dto);
    void deleteReview(Long id);
}