package com.aleprimo.nova_store.controller;


import com.aleprimo.nova_store.dto.shoppingCartItem.ShoppingCartItemRequestDTO;
import com.aleprimo.nova_store.dto.shoppingCartItem.ShoppingCartItemResponseDTO;
import com.aleprimo.nova_store.entityServices.ShoppingCartItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart-items")
@RequiredArgsConstructor
@Tag(name = "Controlador de Item de Carrito de Compras", description = "Operaciones CRUD  de Items de  carritos de compra")
public class ShoppingCartItemController {

    private final ShoppingCartItemService shoppingCartItemService;

    @Operation(summary = "Obtener todos los items de un carrito de compras")
    @GetMapping
    public ResponseEntity<?> findAll(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok(shoppingCartItemService.getAllItems(pageable));
    }

    @Operation(summary = "Obtener un item de un carrito por su Id")
    @GetMapping("/{id}")
    public ResponseEntity<ShoppingCartItemResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(shoppingCartItemService.getItemById(id));
    }

    @Operation(summary = "Crear un nuevo item de carrito")
    @PostMapping("/create")
    public ResponseEntity<ShoppingCartItemResponseDTO> create(@Valid @RequestBody ShoppingCartItemRequestDTO dto) {
        return ResponseEntity.status(201).body(shoppingCartItemService.addItemToCart(dto));
    }

    @Operation(summary = "Actualizar un item")
    @PutMapping("/{id}")
    public ResponseEntity<ShoppingCartItemResponseDTO> update(@PathVariable Long id,
                                                              @Valid @RequestBody ShoppingCartItemRequestDTO dto) {
        return ResponseEntity.ok(shoppingCartItemService.update(id, dto));
    }

    @Operation(summary = "Borra un item de carrito")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        shoppingCartItemService.deleteItem(id);
        return ResponseEntity.noContent().build();
    }
}
