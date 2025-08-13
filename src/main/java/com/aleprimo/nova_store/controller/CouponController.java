package com.aleprimo.nova_store.controller;

import com.aleprimo.nova_store.dto.coupon.CouponRequestDTO;
import com.aleprimo.nova_store.dto.coupon.CouponResponseDTO;
import com.aleprimo.nova_store.entityServices.CouponService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/coupons")
@RequiredArgsConstructor
@Tag(name = "Cupones", description = "CRUD para gestión de cupones de descuento")
public class CouponController {

    private final CouponService couponService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear un nuevo cupón", description = "Registra un nuevo cupón con un porcentaje de descuento.")
    @ApiResponse(responseCode = "201", description = "Cupón creado correctamente")
    @ApiResponse(responseCode = "400", description = "Datos inválidos en la solicitud")
    public CouponResponseDTO create(@Valid @RequestBody CouponRequestDTO dto) {
        return couponService.create(dto);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener cupón por ID", description = "Devuelve la información de un cupón específico.")
    @ApiResponse(responseCode = "200", description = "Cupón encontrado")
    @ApiResponse(responseCode = "404", description = "Cupón no encontrado")
    public ResponseEntity<CouponResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(couponService.getById(id));
    }

    @GetMapping("/code/{code}")
    @Operation(summary = "Obtener cupón por código", description = "Busca un cupón por su código único.")
    @ApiResponse(responseCode = "200", description = "Cupón encontrado")
    @ApiResponse(responseCode = "404", description = "Cupón no encontrado")
    public ResponseEntity<CouponResponseDTO> getByCode(@PathVariable String code) {
        return ResponseEntity.ok(couponService.getByCode(code));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar cupón", description = "Elimina un cupón por su ID.")
    @ApiResponse(responseCode = "204", description = "Cupón eliminado correctamente")
    @ApiResponse(responseCode = "404", description = "Cupón no encontrado")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        couponService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @Operation(summary = "Listar todos los cupones", description = "Devuelve una lista paginada de todos los cupones registrados.")
    @ApiResponse(responseCode = "200", description = "Lista de cupones obtenida correctamente")
    public ResponseEntity<Page<CouponResponseDTO>> getAll(Pageable pageable) {
        return ResponseEntity.ok(couponService.getAll(pageable));
    }
}