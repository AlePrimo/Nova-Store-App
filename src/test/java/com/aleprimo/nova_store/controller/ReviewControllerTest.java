package com.aleprimo.nova_store.controller;

import com.aleprimo.nova_store.dto.request.ReviewRequestDTO;
import com.aleprimo.nova_store.dto.response.ReviewResponseDTO;
import com.aleprimo.nova_store.service.ReviewService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReviewController.class)
class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReviewService reviewService;

    @Autowired
    private ObjectMapper objectMapper;

    private ReviewRequestDTO requestDTO;
    private ReviewResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        requestDTO = ReviewRequestDTO.builder()
                .rating(5)
                .comment("Excelente calidad")
                .productId(1L)
                .customerId(1L)
                .build();

        responseDTO = ReviewResponseDTO.builder()
                .id(1L)
                .rating(5)
                .comment("Excelente calidad")
                .productId(1L)
                .customerId(1L)
                .build();
    }

    @Test
    void testCreateReview() throws Exception {
        Mockito.when(reviewService.createReview(any())).thenReturn(responseDTO);

        mockMvc.perform(post("/api/reviews")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testGetReviewById() throws Exception {
        Mockito.when(reviewService.getReviewById(1L)).thenReturn(responseDTO);

        mockMvc.perform(get("/api/reviews/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testGetAllReviews() throws Exception {
        Pageable pageable = PageRequest.of(0, 10);
        Mockito.when(reviewService.getAllReviews(any()))
                .thenReturn(new PageImpl<>(List.of(responseDTO), pageable, 1));

        mockMvc.perform(get("/api/reviews?page=0&size=10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(1));
    }

    @Test
    void testUpdateReview() throws Exception {
        Mockito.when(reviewService.updateReview(eq(1L), any())).thenReturn(responseDTO);

        mockMvc.perform(put("/api/reviews/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testDeleteReview() throws Exception {
        mockMvc.perform(delete("/api/reviews/1"))
                .andExpect(status().isNoContent());
    }
}
