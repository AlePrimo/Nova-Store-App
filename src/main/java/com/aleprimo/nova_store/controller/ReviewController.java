package com.aleprimo.nova_store.controller;


import com.aleprimo.nova_store.dto.review.ReviewRequestDTO;
import com.aleprimo.nova_store.dto.review.ReviewResponseDTO;
import com.aleprimo.nova_store.entityServices.ReviewService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
@Tag(name = "Review Controller", description = "CRUD de reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ReviewResponseDTO create(@Valid @RequestBody ReviewRequestDTO dto) {
        return reviewService.createReview(dto);
    }

    @GetMapping
    public Page<ReviewResponseDTO> getAll(Pageable pageable) {
        return reviewService.getAllReviews(pageable);
    }

    @GetMapping("/{id}")
    public ReviewResponseDTO getById(@PathVariable Long id) {
        return reviewService.getReviewById(id);
    }

    @PutMapping("/{id}")
    public ReviewResponseDTO update(@PathVariable Long id, @Valid @RequestBody ReviewRequestDTO dto) {
        return reviewService.updateReview(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        reviewService.deleteReview(id);
    }
}
