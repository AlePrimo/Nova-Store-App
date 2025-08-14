package com.aleprimo.nova_store.controller;


import com.aleprimo.nova_store.dto.shipping.ShippingRequestDTO;
import com.aleprimo.nova_store.dto.shipping.ShippingResponseDTO;
import com.aleprimo.nova_store.entityServices.ShippingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@Tag(name = "Envios", description = "Operaciones CRUD sobre Envios")
public class ShippingController {

    private final ShippingService shippingService;

    @PostMapping
    @Operation(summary = "Crear un nuevo envío")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Envío creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos en la solicitud")
    })
    public ResponseEntity<ShippingResponseDTO> create(@Valid @RequestBody ShippingRequestDTO dto) {
        ShippingResponseDTO response = shippingService.createShipping(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener envío por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Envío encontrado"),
            @ApiResponse(responseCode = "404", description = "Envío no encontrado")
    })
    public ResponseEntity<ShippingResponseDTO> findById(@PathVariable Long id) {
        ShippingResponseDTO dto = shippingService.getShippingById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    @Operation(summary = "Listar todos los envíos con paginación")
    @ApiResponse(responseCode = "200", description = "Lista de envíos obtenida correctamente")
    public ResponseEntity<Page<ShippingResponseDTO>> findAll(@ParameterObject Pageable pageable) {
        Page<ShippingResponseDTO> page = shippingService.getAllShippings(pageable);
        return ResponseEntity.ok(page);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar envío por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Envío actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Envío no encontrado")
    })
    public ResponseEntity<ShippingResponseDTO> update(@PathVariable Long id, @Valid @RequestBody ShippingRequestDTO dto) {
        ShippingResponseDTO updated = shippingService.updateShipping(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar envío por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Envío eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Envío no encontrado")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        shippingService.deleteShipping(id);
        return ResponseEntity.noContent().build();
    }
}
