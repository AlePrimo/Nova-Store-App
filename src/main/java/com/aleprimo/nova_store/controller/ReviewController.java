package com.aleprimo.nova_store.controller;


import com.aleprimo.nova_store.dto.review.ReviewRequestDTO;
import com.aleprimo.nova_store.dto.review.ReviewResponseDTO;
import com.aleprimo.nova_store.entityServices.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
@Tag(name = "Controlador de Opinion", description = "Operaciones CRUD para Opiniones")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Generar una opinion sobre un producto")
    public ReviewResponseDTO create(@Valid @RequestBody ReviewRequestDTO dto) {
        return reviewService.createReview(dto);
    }

    @GetMapping
    @Operation(summary = "Obtener todas las opiniones de un producto(con paginacion)")
    public Page<ReviewResponseDTO> getAll(Pageable pageable) {
        return reviewService.getAllReviews(pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una opinion por su Id")
    public ReviewResponseDTO getById(@PathVariable Long id) {
        return reviewService.getReviewById(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una opinion")
    public ReviewResponseDTO update(@PathVariable Long id, @Valid @RequestBody ReviewRequestDTO dto) {
        return reviewService.updateReview(id, dto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Borrar una opinion")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        reviewService.deleteReview(id);
    }
}
