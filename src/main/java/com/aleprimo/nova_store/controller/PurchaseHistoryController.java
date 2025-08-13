package com.aleprimo.nova_store.controller;

import com.aleprimo.nova_store.dto.purchaseHistory.PurchaseHistoryDTO;

import com.aleprimo.nova_store.entityServices.PurchaseHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Controlador de Historial de Compra", description = "Endpoint para obtener el historial de compra de un cliente")
@RestController
@RequestMapping("/api/purchase-history")
@RequiredArgsConstructor
public class PurchaseHistoryController {

    private final PurchaseHistoryService purchaseHistoryService;

    @Operation(summary = "Obtener el historial de compra de un cliente por su Id")
    @GetMapping("/{customerId}")
    public ResponseEntity<Page<PurchaseHistoryDTO>> getPurchaseHistoryByCustomerId(
            @PathVariable Long customerId,
            @ParameterObject Pageable pageable) {
        Page<PurchaseHistoryDTO> history = purchaseHistoryService.getPurchaseHistoryByCustomerId(customerId, pageable);
        return ResponseEntity.ok(history);
    }
}
