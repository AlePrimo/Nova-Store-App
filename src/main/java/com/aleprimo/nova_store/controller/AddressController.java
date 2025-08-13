package com.aleprimo.nova_store.controller;


import com.aleprimo.nova_store.dto.adress.AddressRequestDTO;
import com.aleprimo.nova_store.dto.adress.AddressResponseDTO;
import com.aleprimo.nova_store.entityServices.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/addresses")
@RequiredArgsConstructor
@Tag(name = "Direcciones", description = "CRUD para gestión de direcciones de clientes")
public class AddressController {

    private final AddressService addressService;

    @Operation(summary = "Crear una nueva dirección", description = "Permite registrar una dirección asociada a un cliente.")
    @ApiResponse(responseCode = "201", description = "Dirección creada correctamente")
    @ApiResponse(responseCode = "400", description = "Datos inválidos en la solicitud")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping

    public ResponseEntity<AddressResponseDTO> createAddress(@Valid @RequestBody AddressRequestDTO dto) {
        return ResponseEntity.ok(addressService.createAddress(dto));
    }

    @Operation(summary = "Obtener dirección por ID", description = "Devuelve la información de una dirección específica.")
    @ApiResponse(responseCode = "200", description = "Dirección encontrada")
    @ApiResponse(responseCode = "404", description = "Dirección no encontrada")
    @GetMapping("/{id}")

    public ResponseEntity<AddressResponseDTO> getAddressById(@PathVariable Long id) {
        return ResponseEntity.ok(addressService.getAddressById(id));
    }

    @Operation(summary = "Listar todas las direcciones", description = "Devuelve una lista paginada de todas las direcciones registradas.")
    @ApiResponse(responseCode = "200", description = "Lista de direcciones obtenida correctamente")
    @GetMapping
    public ResponseEntity<Page<AddressResponseDTO>> getAllAddresses(Pageable pageable) {
        return ResponseEntity.ok(addressService.getAllAddresses(pageable));
    }

    @Operation(summary = "Actualizar dirección", description = "Permite modificar los datos de una dirección existente.")
    @ApiResponse(responseCode = "200", description = "Dirección actualizada correctamente")
    @ApiResponse(responseCode = "404", description = "Dirección no encontrada")
    @PutMapping("/{id}")
    public ResponseEntity<AddressResponseDTO> updateAddress(@PathVariable Long id, @Valid @RequestBody AddressRequestDTO dto) {
        return ResponseEntity.ok(addressService.updateAddress(id, dto));
    }

    @Operation(summary = "Eliminar dirección", description = "Elimina una dirección por su ID.")
    @ApiResponse(responseCode = "204", description = "Dirección eliminada correctamente")
    @ApiResponse(responseCode = "404", description = "Dirección no encontrada")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id) {
        addressService.deleteAddress(id);
        return ResponseEntity.noContent().build();
    }
}
