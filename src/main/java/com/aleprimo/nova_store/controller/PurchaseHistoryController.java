package com.aleprimo.nova_store.controller;

import com.aleprimo.nova_store.dto.purchaseHistory.PurchaseHistoryDTO;

import com.aleprimo.nova_store.entityServices.PurchaseHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/purchase-history")
@RequiredArgsConstructor
@Tag(name = "Historial de Compra", description = "Endpoint para obtener el historial de compra de un cliente")
public class PurchaseHistoryController {

    private final PurchaseHistoryService purchaseHistoryService;

    @Operation(summary = "Obtener historial de compra", description = "Devuelve el historial de compras de un cliente por su ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Historial de compra obtenido correctamente"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @GetMapping("/{customerId}")
    public ResponseEntity<Page<PurchaseHistoryDTO>> getPurchaseHistoryByCustomerId(
            @PathVariable Long customerId,
            @ParameterObject Pageable pageable) {
        Page<PurchaseHistoryDTO> history = purchaseHistoryService.getPurchaseHistoryByCustomerId(customerId, pageable);
        return ResponseEntity.ok(history);
    }
}
