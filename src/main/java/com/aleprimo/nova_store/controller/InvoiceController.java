package com.aleprimo.nova_store.controller;

import com.aleprimo.nova_store.dto.invoice.InvoiceRequestDTO;
import com.aleprimo.nova_store.dto.invoice.InvoiceResponseDTO;

import com.aleprimo.nova_store.entityServices.InvoiceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/invoices")
@RequiredArgsConstructor
@Tag(name = "Facturas", description = "CRUD para gestión de facturas")
public class InvoiceController {

    private final InvoiceService invoiceService;

    @Operation(summary = "Listar facturas", description = "Obtiene una lista paginada de todas las facturas.")
    @ApiResponse(responseCode = "200", description = "Lista de facturas obtenida correctamente")
    @GetMapping
    public ResponseEntity<?> findAll(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok(invoiceService.getAllInvoices(pageable));
    }

    @Operation(summary = "Obtener factura por ID", description = "Devuelve la información de una factura específica.")
    @ApiResponse(responseCode = "200", description = "Factura encontrada")
    @ApiResponse(responseCode = "404", description = "Factura no encontrada")
    @GetMapping("/{id}")
    public ResponseEntity<InvoiceResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(invoiceService.getInvoiceById(id));
    }

    @Operation(summary = "Crear factura", description = "Genera una nueva factura.")
    @ApiResponse(responseCode = "201", description = "Factura creada correctamente")
    @ApiResponse(responseCode = "400", description = "Datos inválidos en la solicitud")
    @PostMapping
    public ResponseEntity<InvoiceResponseDTO> create(@Valid @RequestBody InvoiceRequestDTO dto) {
        return ResponseEntity.status(201).body(invoiceService.createInvoice(dto));
    }

    @Operation(summary = "Actualizar factura", description = "Modifica una factura existente.")
    @ApiResponse(responseCode = "200", description = "Factura actualizada correctamente")
    @ApiResponse(responseCode = "404", description = "Factura no encontrada")
    @PutMapping("/{id}")
    public ResponseEntity<InvoiceResponseDTO> update(@PathVariable Long id,
                                                     @Valid @RequestBody InvoiceRequestDTO dto) {
        return ResponseEntity.ok(invoiceService.update(id, dto));
    }

    @Operation(summary = "Eliminar factura", description = "Elimina una factura por su ID.")
    @ApiResponse(responseCode = "204", description = "Factura eliminada correctamente")
    @ApiResponse(responseCode = "404", description = "Factura no encontrada")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        invoiceService.deleteInvoice(id);
        return ResponseEntity.noContent().build();
    }
}
