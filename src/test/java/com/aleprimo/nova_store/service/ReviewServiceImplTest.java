package com.aleprimo.nova_store.service;


import com.aleprimo.nova_store.controller.mappers.ReviewMapper;
import com.aleprimo.nova_store.dto.review.ReviewRequestDTO;
import com.aleprimo.nova_store.dto.review.ReviewResponseDTO;
import com.aleprimo.nova_store.entityServices.implementations.ReviewServiceImpl;
import com.aleprimo.nova_store.models.Customer;
import com.aleprimo.nova_store.models.Product;
import com.aleprimo.nova_store.models.Review;
import com.aleprimo.nova_store.persistence.ReviewDAO;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class ReviewServiceImplTest {

    @InjectMocks
    private ReviewServiceImpl reviewService;

    @Mock
    private ReviewDAO reviewDAO;

    @Mock
    private ReviewMapper reviewMapper;

    private Review review;
    private ReviewRequestDTO requestDTO;
    private ReviewResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        Product product = Product.builder().id(1L).build();
        Customer customer = Customer.builder().id(1L).build();

        review = Review.builder()
                .id(1L)
                .rating(4)
                .comment("Great product")
                .product(product)
                .customer(customer)
                .build();

        requestDTO = ReviewRequestDTO.builder()
                .rating(4)
                .comment("Great product")
                .productId(1L)
                .customerId(1L)
                .build();

        responseDTO = ReviewResponseDTO.builder()
                .id(1L)
                .rating(4)
                .comment("Great product")
                .productId(1L)
                .customerId(1L)
                .build();
    }

    @Test
    void testCreateReview() {
        when(reviewMapper.toEntity(requestDTO)).thenReturn(review);
        when(reviewDAO.save(review)).thenReturn(review);
        when(reviewMapper.toDTO(review)).thenReturn(responseDTO);

        ReviewResponseDTO result = reviewService.createReview(requestDTO);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(reviewDAO).save(review);
    }

    @Test
    void testGetReviewById() {
        when(reviewDAO.findById(1L)).thenReturn(Optional.of(review));
        when(reviewMapper.toDTO(review)).thenReturn(responseDTO);

        ReviewResponseDTO result = reviewService.getReviewById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(reviewDAO).findById(1L);
    }

    @Test
    void testGetReviewById_NotFound() {
        when(reviewDAO.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> reviewService.getReviewById(1L));
    }

    @Test
    void testGetAllReviews() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Review> reviewPage = new PageImpl<>(List.of(review));

        when(reviewDAO.findAll(pageable)).thenReturn(reviewPage);
        when(reviewMapper.toDTO(review)).thenReturn(responseDTO);

        Page<ReviewResponseDTO> result = reviewService.getAllReviews(pageable);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        verify(reviewDAO).findAll(pageable);
    }

    @Test
    void testUpdateReview() {
        when(reviewDAO.findById(1L)).thenReturn(Optional.of(review));
        when(reviewDAO.save(review)).thenReturn(review);
        when(reviewMapper.toDTO(review)).thenReturn(responseDTO);

        ReviewResponseDTO result = reviewService.updateReview(1L, requestDTO);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Great product", result.getComment());
    }

    @Test
    void testUpdateReview_NotFound() {
        when(reviewDAO.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> reviewService.updateReview(1L, requestDTO));
    }

    @Test
    void testDeleteReview() {
        when(reviewDAO.findById(1L)).thenReturn(Optional.of(review));

        reviewService.deleteReview(1L);

        verify(reviewDAO).deleteById(review.getId());
    }

    @Test
    void testDeleteReview_NotFound() {
        when(reviewDAO.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> reviewService.deleteReview(1L));
    }
}
