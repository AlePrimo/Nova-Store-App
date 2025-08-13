package com.aleprimo.nova_store.controller;

import com.aleprimo.nova_store.dto.orderItem.OrderItemRequestDTO;
import com.aleprimo.nova_store.dto.orderItem.OrderItemResponseDTO;
import com.aleprimo.nova_store.entityServices.OrderItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order-items")
@RequiredArgsConstructor
@Tag(name = "Controlador de Items de Ordenes", description = "Operaciones CRUD sobre Items")
public class OrderItemController {

    private final OrderItemService orderItemService;

    @Operation(summary = "Obtener todos los ítems de órdenes (paginado)")
    @GetMapping
    public ResponseEntity<Page<OrderItemResponseDTO>> getAll(Pageable pageable) {
        return ResponseEntity.ok(orderItemService.getAll(pageable));
    }

    @Operation(summary = "Obtener un ítem de orden por ID")
    @GetMapping("/{id}")
    public ResponseEntity<OrderItemResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(orderItemService.getById(id));
    }

    @Operation(summary = "Crear un nuevo ítem de orden")
    @PostMapping
    public ResponseEntity<OrderItemResponseDTO> create(@RequestBody OrderItemRequestDTO dto) {
        return ResponseEntity.ok(orderItemService.create(dto));
    }

    @Operation(summary = "Actualizar un ítem de orden")
    @PutMapping("/{id}")
    public ResponseEntity<OrderItemResponseDTO> update(@PathVariable Long id, @RequestBody OrderItemRequestDTO dto) {
        return ResponseEntity.ok(orderItemService.update(id, dto));
    }

    @Operation(summary = "Eliminar un ítem de orden")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        orderItemService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
