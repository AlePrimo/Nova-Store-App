package com.aleprimo.nova_store.controller.mappers;


import com.aleprimo.nova_store.dto.review.ReviewResponseDTO;
import com.aleprimo.nova_store.models.Review;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {

    public ReviewResponseDTO toDTO(Review review) {
        return new ReviewResponseDTO(
            review.getId(),
            review.getRating(),
            review.getComment(),
            review.getCreatedAt(),
            review.getProduct().getId(),
            review.getCustomer().getId()
        );
    }

    public Review toEntity(ReviewResponseDTO dto) {
        return Review.builder()
                .id(dto.id())
                .rating(dto.rating())
                .comment(dto.comment())
                .createdAt(dto.createdAt())
                .build();
    }
}
