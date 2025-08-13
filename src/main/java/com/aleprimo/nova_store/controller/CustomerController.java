package com.aleprimo.nova_store.controller;

import com.aleprimo.nova_store.dto.customer.CustomerRequestDTO;
import com.aleprimo.nova_store.dto.customer.CustomerResponseDTO;
import com.aleprimo.nova_store.entityServices.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
@Tag(name = "Clientes", description = "CRUD para gestión de clientes")
public class CustomerController {

    private final CustomerService service;

    @GetMapping
    @Operation(summary = "Listar todos los clientes", description = "Obtiene una lista paginada de todos los clientes registrados.")
    @ApiResponse(responseCode = "200", description = "Lista de clientes obtenida correctamente")
    public ResponseEntity<Page<CustomerResponseDTO>> getAll(Pageable pageable) {
        return ResponseEntity.ok(service.getAll(pageable));
    }

    @GetMapping("/active")
    @Operation(summary = "Listar clientes activos", description = "Obtiene una lista paginada de todos los clientes que están activos.")
    @ApiResponse(responseCode = "200", description = "Lista de clientes activos obtenida correctamente")
    public ResponseEntity<Page<CustomerResponseDTO>> getActive(Pageable pageable) {
        return ResponseEntity.ok(service.getActive(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener cliente por ID", description = "Devuelve la información de un cliente específico.")
    @ApiResponse(responseCode = "200", description = "Cliente encontrado")
    @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    public ResponseEntity<CustomerResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    @Operation(summary = "Crear cliente", description = "Registra un nuevo cliente.")
    @ApiResponse(responseCode = "201", description = "Cliente creado correctamente")
    @ApiResponse(responseCode = "400", description = "Datos inválidos en la solicitud")
    public ResponseEntity<CustomerResponseDTO> create(@RequestBody CustomerRequestDTO dto) {
        return new ResponseEntity<>(service.create(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar cliente", description = "Modifica la información de un cliente existente.")
    @ApiResponse(responseCode = "200", description = "Cliente actualizado correctamente")
    @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    public ResponseEntity<CustomerResponseDTO> update(@PathVariable Long id, @RequestBody CustomerRequestDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar cliente", description = "Elimina un cliente por su ID.")
    @ApiResponse(responseCode = "204", description = "Cliente eliminado correctamente")
    @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
