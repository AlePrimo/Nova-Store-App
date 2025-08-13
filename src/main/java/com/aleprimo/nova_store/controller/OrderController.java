package com.aleprimo.nova_store.controller;


import com.aleprimo.nova_store.dto.order.OrderRequestDTO;
import com.aleprimo.nova_store.dto.order.OrderResponseDTO;
import com.aleprimo.nova_store.entityServices.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Tag(name = "Órdenes", description = "CRUD y operaciones sobre órdenes de compra")
public class OrderController {

    private final OrderService orderService;


    @Operation(summary = "Listar órdenes", description = "Obtiene una lista paginada de todas las órdenes.")
    @ApiResponse(responseCode = "200", description = "Lista de órdenes obtenida correctamente")
    @GetMapping
    public ResponseEntity<Page<OrderResponseDTO>> getAll(Pageable pageable) {
        return ResponseEntity.ok(orderService.getAll(pageable));
    }


    @Operation(summary = "Obtener orden por ID", description = "Devuelve la información de una orden específica.")
    @ApiResponse(responseCode = "200", description = "Orden encontrada")
    @ApiResponse(responseCode = "404", description = "Orden no encontrada")
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getById(id));
    }

    @Operation(summary = "Crear orden", description = "Registra una nueva orden de compra.")
    @ApiResponse(responseCode = "201", description = "Orden creada correctamente")
    @ApiResponse(responseCode = "400", description = "Datos inválidos en la solicitud")
    @PostMapping
    public ResponseEntity<OrderResponseDTO> create(@RequestBody OrderRequestDTO dto) {
        return ResponseEntity.ok(orderService.create(dto));
    }

    @Operation(summary = "Actualizar orden", description = "Modifica los datos de una orden existente.")
    @ApiResponse(responseCode = "200", description = "Orden actualizada correctamente")
    @ApiResponse(responseCode = "404", description = "Orden no encontrada")
    @PutMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> update(@PathVariable Long id, @RequestBody OrderRequestDTO dto) {
        return ResponseEntity.ok(orderService.update(id, dto));
    }

    @Operation(summary = "Eliminar orden", description = "Elimina una orden por su ID.")
    @ApiResponse(responseCode = "204", description = "Orden eliminada correctamente")
    @ApiResponse(responseCode = "404", description = "Orden no encontrada")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        orderService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
