package com.aleprimo.nova_store.controller;

import com.aleprimo.nova_store.dto.customer.CustomerRequestDTO;
import com.aleprimo.nova_store.dto.customer.CustomerResponseDTO;
import com.aleprimo.nova_store.entityServices.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
@Tag(name = "Controlador de Clientes", description = "CRUD para gestión de clientes")
public class CustomerController {

    private final CustomerService service;

    @GetMapping
    @Operation(summary = "Obtener la lista completa de Clientes(con paginacion)")
    public ResponseEntity<Page<CustomerResponseDTO>> getAll(Pageable pageable) {
        return ResponseEntity.ok(service.getAll(pageable));
    }

    @GetMapping("/active")
    @Operation(summary = "Obtener todos los clientes activos")
    public ResponseEntity<Page<CustomerResponseDTO>> getActive(Pageable pageable) {
        return ResponseEntity.ok(service.getActive(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un cliente por su Id")
    public ResponseEntity<CustomerResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo cliente")
    public ResponseEntity<CustomerResponseDTO> create(@RequestBody CustomerRequestDTO dto) {
        return new ResponseEntity<>(service.create(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar los datos de un cliente")
    public ResponseEntity<CustomerResponseDTO> update(@PathVariable Long id, @RequestBody CustomerRequestDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Borrar un cliente por su Id")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
