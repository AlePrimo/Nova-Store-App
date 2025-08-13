package com.aleprimo.nova_store.controller;

import com.aleprimo.nova_store.controller.mappers.CategoryMapper;
import com.aleprimo.nova_store.dto.category.CategoryRequestDTO;
import com.aleprimo.nova_store.dto.category.CategoryResponseDTO;
import com.aleprimo.nova_store.entityServices.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@Validated
@Tag(name = "Controlador de Categorías", description = "Operaciones CRUD sobre categorías de productos")
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @Operation(summary = "Crear una nueva categoría")
    @ApiResponse(responseCode = "201", description = "Categoría creada correctamente")
    @PostMapping("/create")
    public ResponseEntity<CategoryResponseDTO> create(@RequestBody @Validated CategoryRequestDTO dto) {
        CategoryResponseDTO created = categoryService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(summary = "Listar todas las categorías con paginación")
    @ApiResponse(responseCode = "200", description = "Página de categorías obtenida correctamente")
    @GetMapping("/all")
    public ResponseEntity<Page<CategoryResponseDTO>> getAll(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok(categoryService.getAll(pageable));
    }

    @Operation(summary = "Listar solo las categorías activas con paginación")
    @ApiResponse(responseCode = "200", description = "Página de categorías activas obtenida correctamente")
    @GetMapping("/active")
    public ResponseEntity<Page<CategoryResponseDTO>> getActive(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok(categoryService.getActive(pageable));
    }

    @Operation(summary = "Buscar una categoría por su ID")
    @ApiResponse(responseCode = "200", description = "Categoría encontrada")
    @GetMapping("/id/{id}")
    public ResponseEntity<CategoryResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.getById(id));
    }

    @Operation(summary = "Buscar categorías por nombre (contiene)")
    @ApiResponse(responseCode = "200", description = "Lista de categorías coincidentes")
    @GetMapping("/search")
    public ResponseEntity<List<CategoryResponseDTO>> searchByName(
            @RequestParam("name") @Schema(example = "tecnología") String name
    ) {
        return ResponseEntity.ok(categoryService.searchByName(name));
    }

    @Operation(summary = "Actualizar una categoría existente")
    @ApiResponse(responseCode = "200", description = "Categoría actualizada correctamente")
    @PutMapping("/update/{id}")
    public ResponseEntity<CategoryResponseDTO> update(
            @PathVariable Long id,
            @RequestBody @Validated CategoryRequestDTO dto
    ) {
        return ResponseEntity.ok(categoryService.update(id, dto));
    }

    @Operation(summary = "Eliminar una categoría por ID")
    @ApiResponse(responseCode = "204", description = "Categoría eliminada correctamente")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        categoryService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
