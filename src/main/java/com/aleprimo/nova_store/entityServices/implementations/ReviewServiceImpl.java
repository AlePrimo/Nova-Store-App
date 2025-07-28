package com.aleprimo.nova_store.entityServices.implementations;


import com.aleprimo.nova_store.controller.mappers.ReviewMapper;
import com.aleprimo.nova_store.dto.review.ReviewRequestDTO;
import com.aleprimo.nova_store.dto.review.ReviewResponseDTO;
import com.aleprimo.nova_store.entityServices.ReviewService;
import com.aleprimo.nova_store.handler.exceptions.ResourceNotFoundException;
import com.aleprimo.nova_store.models.Customer;
import com.aleprimo.nova_store.models.Product;
import com.aleprimo.nova_store.models.Review;
import com.aleprimo.nova_store.repository.CustomerRepository;
import com.aleprimo.nova_store.repository.ProductRepository;
import com.aleprimo.nova_store.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final ReviewMapper reviewMapper;

    @Override
    public ReviewResponseDTO createReview(ReviewRequestDTO dto) {
        Product product = productRepository.findById(dto.getProductId())
            .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + dto.getProductId()));
        Customer customer = customerRepository.findById(dto.getCustomerId())
            .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con id: " + dto.getCustomerId()));

        Review review = Review.builder()
                .rating(dto.getRating())
                .comment(dto.getComment())
                .createdAt(LocalDateTime.now())
                .product(product)
                .customer(customer)
                .build();

        return reviewMapper.toDTO(reviewRepository.save(review));
    }

    @Override
    public Page<ReviewResponseDTO> getAllReviews(Pageable pageable) {
        return reviewRepository.findAll(pageable).map(reviewMapper::toDTO);
    }

    @Override
    public ReviewResponseDTO getReviewById(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review no encontrado con id: " + id));
        return reviewMapper.toDTO(review);
    }

    @Override
    public ReviewResponseDTO updateReview(Long id, ReviewRequestDTO dto) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review no encontrado con id: " + id));

        review.setRating(dto.getRating());
        review.setComment(dto.getComment());
        return reviewMapper.toDTO(reviewRepository.save(review));
    }

    @Override
    public void deleteReview(Long id) {
        if (!reviewRepository.existsById(id)) {
            throw new ResourceNotFoundException("Review no encontrado con id: " + id);
        }
        reviewRepository.deleteById(id);
    }
}
