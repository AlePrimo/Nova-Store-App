package com.aleprimo.nova_store.controller;

import com.aleprimo.nova_store.dto.payment.PaymentRequestDTO;
import com.aleprimo.nova_store.dto.payment.PaymentResponseDTO;

import com.aleprimo.nova_store.entityServices.PaymentService;
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
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@Tag(name = "Pagos", description = "Operaciones CRUD sobre pagos")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    @Operation(summary = "Crear pago", description = "Registra un nuevo pago.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Pago creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos en la solicitud")
    })
    public ResponseEntity<PaymentResponseDTO> create(@Valid @RequestBody PaymentRequestDTO dto) {
        PaymentResponseDTO created = paymentService.create(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar pago", description = "Modifica un pago existente por su ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pago actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Pago no encontrado")
    })
    public ResponseEntity<PaymentResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody PaymentRequestDTO dto
    ) {
        PaymentResponseDTO updated = paymentService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")

    @Operation(summary = "Eliminar pago", description = "Elimina un pago por su ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Pago eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Pago no encontrado")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        paymentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener pago por ID", description = "Devuelve un pago específico.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pago encontrado"),
            @ApiResponse(responseCode = "404", description = "Pago no encontrado")
    })
    public ResponseEntity<PaymentResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(paymentService.getById(id));
    }

    @GetMapping
    @Operation(summary = "Listar pagos", description = "Obtiene una lista paginada de todos los pagos.")
    @ApiResponse(responseCode = "200", description = "Lista de pagos obtenida correctamente")
    public ResponseEntity<Page<PaymentResponseDTO>> getAll(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok(paymentService.getAll(pageable));
    }
}
