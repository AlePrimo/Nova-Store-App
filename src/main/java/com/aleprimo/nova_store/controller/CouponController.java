package com.aleprimo.nova_store.controller;

import com.aleprimo.nova_store.dto.coupon.CouponRequestDTO;
import com.aleprimo.nova_store.dto.coupon.CouponResponseDTO;
import com.aleprimo.nova_store.entityServices.CouponService;
import io.swagger.v3.oas.annotations.Operation;
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
@Tag(name = "Controlador de  Cupones", description = "CRUD para gestión de cupones")
public class CouponController {

    private final CouponService couponService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear un nuevo cupon")
    public CouponResponseDTO create(@Valid @RequestBody CouponRequestDTO dto) {
        return couponService.create(dto);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un cupon por su Id")
    public ResponseEntity<CouponResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(couponService.getById(id));
    }

    @GetMapping("/code/{code}")
    @Operation(summary = "Obtener un cupon por su codigo")
    public ResponseEntity<CouponResponseDTO> getByCode(@PathVariable String code) {
        return ResponseEntity.ok(couponService.getByCode(code));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Borrar un cupon")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        couponService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @Operation(summary = "Obtener todos los cupones(con paginacion)")
    public ResponseEntity<Page<CouponResponseDTO>> getAll(Pageable pageable) {
        return ResponseEntity.ok(couponService.getAll(pageable));
    }
}