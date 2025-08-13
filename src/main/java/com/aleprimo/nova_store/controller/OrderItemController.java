package com.aleprimo.nova_store.controller;

import com.aleprimo.nova_store.dto.orderItem.OrderItemRequestDTO;
import com.aleprimo.nova_store.dto.orderItem.OrderItemResponseDTO;
import com.aleprimo.nova_store.entityServices.OrderItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order-items")
@RequiredArgsConstructor
@Tag(name = "Ítems de Órdenes", description = "Operaciones CRUD sobre ítems de orden")
public class OrderItemController {

    private final OrderItemService orderItemService;

    @Operation(summary = "Listar ítems de órdenes", description = "Obtiene una lista paginada de todos los ítems de órdenes.")
    @ApiResponse(responseCode = "200", description = "Lista de ítems obtenida correctamente")
    @GetMapping
    public ResponseEntity<Page<OrderItemResponseDTO>> getAll(Pageable pageable) {
        return ResponseEntity.ok(orderItemService.getAll(pageable));
    }

    @Operation(summary = "Obtener ítem por ID", description = "Devuelve un ítem de orden específico.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ítem encontrado"),
            @ApiResponse(responseCode = "404", description = "Ítem no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<OrderItemResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(orderItemService.getById(id));
    }

    @Operation(summary = "Crear ítem de orden", description = "Agrega un nuevo ítem a una orden.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Ítem creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos en la solicitud")
    })
    @PostMapping
    public ResponseEntity<OrderItemResponseDTO> create(@RequestBody OrderItemRequestDTO dto) {
        return ResponseEntity.ok(orderItemService.create(dto));
    }

    @Operation(summary = "Actualizar ítem de orden", description = "Modifica los datos de un ítem de orden existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ítem actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Ítem no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<OrderItemResponseDTO> update(@PathVariable Long id, @RequestBody OrderItemRequestDTO dto) {
        return ResponseEntity.ok(orderItemService.update(id, dto));
    }

    @Operation(summary = "Eliminar ítem de orden", description = "Elimina un ítem de orden por su ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Ítem eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Ítem no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        orderItemService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
