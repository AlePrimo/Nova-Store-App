package com.aleprimo.nova_store.controller;


import com.aleprimo.nova_store.dto.whishlist.WishlistRequestDTO;
import com.aleprimo.nova_store.dto.whishlist.WishlistResponseDTO;
import com.aleprimo.nova_store.entityServices.WishlistService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/wishlists")
@RequiredArgsConstructor
@Tag(name = "Lista de Deseos", description = "Operaciones CRUD sobre una Lista de Deseos")
public class WishlistController {

    private final WishlistService wishlistService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear una Lista de deseos")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Lista de deseos creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos en la solicitud")
    })
    public WishlistResponseDTO create(@Valid @RequestBody WishlistRequestDTO dto) {
        return wishlistService.create(dto);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una lista de deseos por su Id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de deseos encontrada"),
            @ApiResponse(responseCode = "404", description = "Lista de deseos no encontrada")
    })
    public WishlistResponseDTO getById(@PathVariable Long id) {
        return wishlistService.getById(id);
    }

    @GetMapping
    @Operation(summary = "Obtener todas las listas de deseos (con paginación)")
    @ApiResponse(responseCode = "200", description = "Listas de deseos obtenidas correctamente")
    public org.springframework.data.domain.Page<WishlistResponseDTO> getAll(@ParameterObject Pageable pageable) {
        return wishlistService.getAll(pageable);
    }
    @Operation(summary = "Actualizar una lista de deseos")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de deseos actualizada correctamente"),
            @ApiResponse(responseCode = "404", description = "Lista de deseos no encontrada"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos en la solicitud")
    })
    @PutMapping("/{id}")
    public ResponseEntity<WishlistResponseDTO> updateWishlist(@PathVariable Long id, @Valid @RequestBody WishlistRequestDTO requestDTO) {
        return ResponseEntity.ok(wishlistService.updateWishlist(id, requestDTO));
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Eliminar una lista de deseos")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Lista de deseos eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "Lista de deseos no encontrada")
    })
    public void delete(@PathVariable Long id) {
        wishlistService.delete(id);
    }
}
