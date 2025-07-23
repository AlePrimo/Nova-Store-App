package com.aleprimo.nova_store.controller;


import com.aleprimo.nova_store.dto.shoppingCart.ShoppingCartRequestDTO;
import com.aleprimo.nova_store.dto.shoppingCart.ShoppingCartResponseDTO;
import com.aleprimo.nova_store.entityServices.ShoppingCartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/shopping-carts")
@RequiredArgsConstructor
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    @Operation(summary = "List all shopping carts (paginated)")
    @GetMapping
    public ResponseEntity<?> findAll(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok(shoppingCartService.getAllCarts(pageable));
    }

    @Operation(summary = "Get a shopping cart by ID")
    @GetMapping("/{id}")
    public ResponseEntity<ShoppingCartResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(shoppingCartService.getCartById(id));
    }

    @Operation(summary = "Create a new shopping cart")
    @PostMapping
    public ResponseEntity<ShoppingCartResponseDTO> create(@Valid @RequestBody ShoppingCartRequestDTO dto) {
        return ResponseEntity.status(201).body(shoppingCartService.createCart(dto));
    }

    @Operation(summary = "Update a shopping cart")
    @PutMapping("/{id}")
    public ResponseEntity<ShoppingCartResponseDTO> update(@PathVariable Long id,
                                                          @Valid @RequestBody ShoppingCartRequestDTO dto) {
        return ResponseEntity.ok(shoppingCartService.update(id,dto));
    }

    @Operation(summary = "Delete a shopping cart")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        shoppingCartService.deleteCart(id);
        return ResponseEntity.noContent().build();
    }
}
