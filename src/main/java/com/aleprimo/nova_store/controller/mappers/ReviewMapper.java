package com.aleprimo.nova_store.controller.mappers;

import com.aleprimo.nova_store.dto.review.ReviewRequestDTO;
import com.aleprimo.nova_store.dto.review.ReviewResponseDTO;
import com.aleprimo.nova_store.models.Customer;
import com.aleprimo.nova_store.models.Product;
import com.aleprimo.nova_store.models.Review;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ReviewMapper {

    public ReviewResponseDTO toDTO(Review review) {
        return ReviewResponseDTO.builder()
                .id(review.getId())
                .rating(review.getRating())
                .comment(review.getComment())
                .createdAt(review.getCreatedAt())
                .productId(review.getProduct().getId())
                .customerId(review.getCustomer().getId())
                .build();
    }

    public Review toEntity(ReviewRequestDTO dto) {
        return Review.builder()
                .rating(dto.getRating())
                .comment(dto.getComment())
                .createdAt(LocalDateTime.now())
                .product(Product.builder().id(dto.getProductId()).build())
                .customer(Customer.builder().id(dto.getCustomerId()).build())
                .build();
    }

    public void updateEntityFromDto(ReviewRequestDTO dto, Review entity) {
        entity.setRating(dto.getRating());
        entity.setComment(dto.getComment());
    }
}
