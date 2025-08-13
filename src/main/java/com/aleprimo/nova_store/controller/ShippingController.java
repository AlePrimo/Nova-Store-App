package com.aleprimo.nova_store.controller;


import com.aleprimo.nova_store.dto.shipping.ShippingRequestDTO;
import com.aleprimo.nova_store.dto.shipping.ShippingResponseDTO;
import com.aleprimo.nova_store.entityServices.ShippingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/shippings")
@RequiredArgsConstructor
@Tag(name = "Controlador de Envios", description = "Operaciones CRUD sobre Envios")
public class ShippingController {

    private final ShippingService shippingService;

    @PostMapping
    @Operation(summary = "Crear un nuevo envío")
    public ResponseEntity<ShippingResponseDTO> create(@Valid @RequestBody ShippingRequestDTO dto) {
        ShippingResponseDTO response = shippingService.createShipping(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener envío por ID")
    public ResponseEntity<ShippingResponseDTO> findById(@PathVariable Long id) {
        ShippingResponseDTO dto = shippingService.getShippingById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    @Operation(summary = "Listar todos los envíos con paginación")
    public ResponseEntity<Page<ShippingResponseDTO>> findAll(@ParameterObject Pageable pageable) {
        Page<ShippingResponseDTO> page = shippingService.getAllShippings(pageable);
        return ResponseEntity.ok(page);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar envío por ID")
    public ResponseEntity<ShippingResponseDTO> update(@PathVariable Long id, @Valid @RequestBody ShippingRequestDTO dto) {
        ShippingResponseDTO updated = shippingService.updateShipping(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar envío por ID")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        shippingService.deleteShipping(id);
        return ResponseEntity.noContent().build();
    }
}
