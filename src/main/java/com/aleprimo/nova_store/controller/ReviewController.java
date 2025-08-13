package com.aleprimo.nova_store.controller;


import com.aleprimo.nova_store.dto.review.ReviewRequestDTO;
import com.aleprimo.nova_store.dto.review.ReviewResponseDTO;
import com.aleprimo.nova_store.entityServices.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@Tag(name = "Opiniones", description = "Operaciones CRUD para opiniones de productos")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear opinión", description = "Genera una nueva opinión sobre un producto.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Opinión creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos en la solicitud")
    })
    public ReviewResponseDTO create(@Valid @RequestBody ReviewRequestDTO dto) {
        return reviewService.createReview(dto);
    }

    @GetMapping

    @Operation(summary = "Listar opiniones", description = "Obtiene todas las opiniones con paginación.")
    @ApiResponse(responseCode = "200", description = "Opiniones obtenidas correctamente")
    public Page<ReviewResponseDTO> getAll(Pageable pageable) {
        return reviewService.getAllReviews(pageable);
    }

    @GetMapping("/{id}")

    @Operation(summary = "Obtener opinión por ID", description = "Devuelve una opinión específica por su ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Opinión encontrada"),
            @ApiResponse(responseCode = "404", description = "Opinión no encontrada")
    })
    public ReviewResponseDTO getById(@PathVariable Long id) {
        return reviewService.getReviewById(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar opinión", description = "Modifica una opinión existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Opinión actualizada correctamente"),
            @ApiResponse(responseCode = "404", description = "Opinión no encontrada")
    })
    public ReviewResponseDTO update(@PathVariable Long id, @Valid @RequestBody ReviewRequestDTO dto) {
        return reviewService.updateReview(id, dto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar opinión", description = "Elimina una opinión por su ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Opinión eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "Opinión no encontrada")
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        reviewService.deleteReview(id);
    }
}
