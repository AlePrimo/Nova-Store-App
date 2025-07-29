package com.aleprimo.nova_store.service.impl;


import com.aleprimo.nova_store.controller.mappers.ReviewMapper;
import com.aleprimo.nova_store.dto.review.ReviewRequestDTO;
import com.aleprimo.nova_store.dto.review.ReviewResponseDTO;
import com.aleprimo.nova_store.entityServices.implementations.ReviewServiceImpl;
import com.aleprimo.nova_store.handler.exceptions.ResourceNotFoundException;
import com.aleprimo.nova_store.models.Review;
import com.aleprimo.nova_store.persistence.ReviewDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReviewServiceImplTest {

    @Mock
    private ReviewDAO reviewDAO;

    @Mock
    private ReviewMapper reviewMapper;

    @InjectMocks
    private ReviewServiceImpl reviewService;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateReview() {
        ReviewRequestDTO dto = new ReviewRequestDTO(5, "Excelente", 1L, 1L);
        Review entity = new Review();
        Review savedEntity = new Review();
        ReviewResponseDTO responseDTO = new ReviewResponseDTO(1L, 5, "Excelente", 1L, 1L);

        when(reviewMapper.toEntity(dto)).thenReturn(entity);
        when(reviewDAO.save(entity)).thenReturn(savedEntity);
        when(reviewMapper.toDTO(savedEntity)).thenReturn(responseDTO);

        ReviewResponseDTO result = reviewService.createReview(dto);

        assertEquals(responseDTO, result);
        verify(reviewMapper).toEntity(dto);
        verify(reviewDAO).save(entity);
        verify(reviewMapper).toDTO(savedEntity);
    }

    @Test
    void testGetReviewById_found() {
        Long id = 1L;
        Review entity = new Review();
        ReviewResponseDTO responseDTO = new ReviewResponseDTO(id, 4, "Muy bueno", 2L, 3L);

        when(reviewDAO.findById(id)).thenReturn(Optional.of(entity));
        when(reviewMapper.toDTO(entity)).thenReturn(responseDTO);

        ReviewResponseDTO result = reviewService.getReviewById(id);

        assertEquals(responseDTO, result);
        verify(reviewDAO).findById(id);
        verify(reviewMapper).toDTO(entity);
    }

    @Test
    void testGetReviewById_notFound() {
        Long id = 99L;
        when(reviewDAO.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> reviewService.getReviewById(id));
        verify(reviewDAO).findById(id);
    }

    @Test
    void testGetAllReviews() {
        Review entity = new Review();
        ReviewResponseDTO responseDTO = ReviewResponseDTO.builder()
                .id(1L)
                .rating(3)
                .comment("Bueno")
                .productId(1L)
                .customerId(2L)
                .build();




        PageRequest pageable = PageRequest.of(0, 10);

        Page<Review> page = new PageImpl<>(List.of(entity));
        when(reviewDAO.findAll(pageable)).thenReturn(page);
        when(reviewMapper.toDTO(entity)).thenReturn(responseDTO);

        Page<ReviewResponseDTO> result = reviewService.getAllReviews(pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals(responseDTO, result.getContent().get(0));
        verify(reviewDAO).findAll(pageable);
    }

    @Test
    void testUpdateReview_found() {
        Long id = 1L;
        ReviewRequestDTO dto = new ReviewRequestDTO(4, "Modificado", 2L, 3L);
        Review entity = new Review();
        Review updated = new Review();
        ReviewResponseDTO responseDTO = ReviewResponseDTO.builder()
                .id(id)
                .rating(4)
                .comment("Modificado")
                .productId(2L)
                .customerId(3L)
                .build();




                

        when(reviewDAO.findById(id)).thenReturn(Optional.of(entity));
        when(reviewDAO.save(entity)).thenReturn(updated);
        when(reviewMapper.toDTO(updated)).thenReturn(responseDTO);

        ReviewResponseDTO result = reviewService.updateReview(id, dto);

        assertEquals(responseDTO, result);
        verify(reviewDAO).findById(id);
        verify(reviewDAO).save(entity);
        verify(reviewMapper).toDTO(updated);
    }

    @Test
    void testUpdateReview_notFound() {
        Long id = 99L;
        ReviewRequestDTO dto = new ReviewRequestDTO(3, "Comentario", 2L, 1L);

        when(reviewDAO.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> reviewService.updateReview(id, dto));
        verify(reviewDAO).findById(id);
    }

    @Test
    void testDeleteReview_found() {
        Long id = 1L;
        when(reviewDAO.existsById(id)).thenReturn(true);

        reviewService.deleteReview(id);

        verify(reviewDAO).existsById(id);
        verify(reviewDAO).deleteById(id);
    }

    @Test
    void testDeleteReview_notFound() {
        Long id = 100L;
        when(reviewDAO.existsById(id)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> reviewService.deleteReview(id));
        verify(reviewDAO).existsById(id);
    }
}