package com.aleprimo.nova_store.controller;

import com.aleprimo.nova_store.dto.invoice.InvoiceRequestDTO;
import com.aleprimo.nova_store.dto.invoice.InvoiceResponseDTO;

import com.aleprimo.nova_store.entityServices.InvoiceService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/invoices")
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceService invoiceService;

    @Operation(summary = "List all invoices (paginated)")
    @GetMapping
    public ResponseEntity<?> findAll(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok(invoiceService.getAllInvoices(pageable));
    }

    @Operation(summary = "Get an invoice by ID")
    @GetMapping("/{id}")
    public ResponseEntity<InvoiceResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(invoiceService.getInvoiceById(id));
    }

    @Operation(summary = "Create a new invoice")
    @PostMapping
    public ResponseEntity<InvoiceResponseDTO> create(@Valid @RequestBody InvoiceRequestDTO dto) {
        return ResponseEntity.status(201).body(invoiceService.createInvoice(dto));
    }

    @Operation(summary = "Update an invoice")
    @PutMapping("/{id}")
    public ResponseEntity<InvoiceResponseDTO> update(@PathVariable Long id,
                                                     @Valid @RequestBody InvoiceRequestDTO dto) {
        return ResponseEntity.ok(invoiceService.update(id, dto));
    }

    @Operation(summary = "Delete an invoice")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        invoiceService.deleteInvoice(id);
        return ResponseEntity.noContent().build();
    }
}
