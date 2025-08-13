package com.aleprimo.nova_store.controller;

import com.aleprimo.nova_store.controller.mappers.ProductMapper;
import com.aleprimo.nova_store.dto.product.ProductRequestDTO;
import com.aleprimo.nova_store.dto.product.ProductResponseDTO;
import com.aleprimo.nova_store.entityServices.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "Controlador de Productos", description = "Operaciones CRUD sobre productos")
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    @Operation(summary = "Crear un nuevo producto")
    @ApiResponse(responseCode = "201", description = "Producto creado correctamente")
    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(@Valid @RequestBody ProductRequestDTO dto) {
        ProductResponseDTO created = productService.createProduct(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(summary = "Obtener todos los productos paginados")
    @ApiResponse(responseCode = "200", description = "Productos obtenidos correctamente")
    @GetMapping("/all")
    public ResponseEntity<Page<ProductResponseDTO>> getAll(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok(productService.getAllProducts(pageable));
    }

    @Operation(summary = "Obtener todos los productos activos")
    @ApiResponse(responseCode = "200", description = "Productos activos obtenidos correctamente")
    @GetMapping("/active")
    public ResponseEntity<Page<ProductResponseDTO>> getActive(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok(productService.getActiveProducts(pageable));
    }

    @Operation(summary = "Buscar productos por nombre")
    @ApiResponse(responseCode = "200", description = "Productos encontrados")
    @GetMapping("/search/name")
    public ResponseEntity<Page<ProductResponseDTO>> searchByName(
            @RequestParam String name,
            @ParameterObject Pageable pageable
    ) {
        return ResponseEntity.ok(productService.searchByName(name, pageable));
    }

    @Operation(summary = "Obtener productos por categoría")
    @ApiResponse(responseCode = "200", description = "Productos filtrados por categoría")
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<Page<ProductResponseDTO>> getByCategory(
            @PathVariable Long categoryId,
            @ParameterObject Pageable pageable
    ) {
        return ResponseEntity.ok(productService.getProductsByCategory(categoryId, pageable));
    }

    @Operation(summary = "Buscar productos por categoría y nombre")
    @ApiResponse(responseCode = "200", description = "Productos encontrados por nombre y categoría")
    @GetMapping("/search")
    public ResponseEntity<Page<ProductResponseDTO>> searchByCategoryAndName(
            @RequestParam Long categoryId,
            @RequestParam String name,
            @ParameterObject Pageable pageable
    ) {
        return ResponseEntity.ok(productService.searchByCategoryAndName(categoryId, name, pageable));
    }

    @GetMapping("/sku/{sku}")
    @Operation(summary = "Obtener un producto por SKU")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto encontrado"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    public ResponseEntity<ProductResponseDTO> getBySku(@PathVariable String sku) {
        return productService.getBySku(sku)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Actualizar un producto existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequestDTO dto
    ) {
        ProductResponseDTO updated = productService.updateProduct(id, dto);
        return ResponseEntity.ok(updated);
    }


    @Operation(summary = "Eliminar un producto por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Producto eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
