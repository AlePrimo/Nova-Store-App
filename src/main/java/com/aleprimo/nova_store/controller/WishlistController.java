package com.aleprimo.nova_store.controller;


import com.aleprimo.nova_store.dto.whishlist.WishlistRequestDTO;
import com.aleprimo.nova_store.dto.whishlist.WishlistResponseDTO;
import com.aleprimo.nova_store.entityServices.WishlistService;
import io.swagger.v3.oas.annotations.Operation;
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
@Tag(name = "Wishlist Controller", description = "Wishlist management endpoints")
public class WishlistController {

    private final WishlistService wishlistService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a wishlist")
    public WishlistResponseDTO create(@Valid @RequestBody WishlistRequestDTO dto) {
        return wishlistService.create(dto);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get wishlist by ID")
    public WishlistResponseDTO getById(@PathVariable Long id) {
        return wishlistService.getById(id);
    }

    @GetMapping
    @Operation(summary = "Get all wishlists (paginated)")
    public org.springframework.data.domain.Page<WishlistResponseDTO> getAll(@ParameterObject Pageable pageable) {
        return wishlistService.getAll(pageable);
    }
    @Operation(summary = "Update a wishlist by ID")
    @PutMapping("/{id}")
    public ResponseEntity<WishlistResponseDTO> updateWishlist(@PathVariable Long id, @Valid @RequestBody WishlistRequestDTO requestDTO) {
        return ResponseEntity.ok(wishlistService.updateWishlist(id, requestDTO));
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete wishlist by ID")
    public void delete(@PathVariable Long id) {
        wishlistService.delete(id);
    }
}
