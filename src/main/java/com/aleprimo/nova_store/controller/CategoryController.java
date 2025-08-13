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
@Tag(name = "Categorías", description = "Operaciones CRUD sobre categorías de productos")
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @Operation(summary = "Crear una nueva categoría", description = "Permite registrar una nueva categoría de productos.")
    @ApiResponse(responseCode = "201", description = "Categoría creada correctamente")
    @ApiResponse(responseCode = "400", description = "Datos inválidos en la solicitud")
    @PostMapping("/create")
    public ResponseEntity<CategoryResponseDTO> create(@RequestBody @Validated CategoryRequestDTO dto) {
        CategoryResponseDTO created = categoryService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }


    @Operation(summary = "Listar todas las categorías", description = "Devuelve una lista paginada de todas las categorías.")
    @ApiResponse(responseCode = "200", description = "Lista de categorías obtenida correctamente")
    @GetMapping("/all")
    public ResponseEntity<Page<CategoryResponseDTO>> getAll(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok(categoryService.getAll(pageable));
    }

    @Operation(summary = "Listar categorías activas", description = "Devuelve una lista paginada de categorías que están activas.")
    @ApiResponse(responseCode = "200", description = "Lista de categorías activas obtenida correctamente")
    @GetMapping("/active")
    public ResponseEntity<Page<CategoryResponseDTO>> getActive(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok(categoryService.getActive(pageable));
    }

    @Operation(summary = "Buscar categoría por ID", description = "Devuelve la información de una categoría específica.")
    @ApiResponse(responseCode = "200", description = "Categoría encontrada")
    @ApiResponse(responseCode = "404", description = "Categoría no encontrada")
    @GetMapping("/id/{id}")
    public ResponseEntity<CategoryResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.getById(id));
    }

    @Operation(summary = "Buscar categorías por nombre", description = "Busca categorías cuyo nombre contenga el texto indicado.")
    @ApiResponse(responseCode = "200", description = "Lista de categorías coincidentes")
    @GetMapping("/search")
    public ResponseEntity<List<CategoryResponseDTO>> searchByName(
            @RequestParam("name") @Schema(example = "tecnología") String name
    ) {
        return ResponseEntity.ok(categoryService.searchByName(name));
    }

    @Operation(summary = "Actualizar categoría", description = "Permite modificar los datos de una categoría existente.")
    @ApiResponse(responseCode = "200", description = "Categoría actualizada correctamente")
    @ApiResponse(responseCode = "404", description = "Categoría no encontrada")
    @PutMapping("/update/{id}")
    public ResponseEntity<CategoryResponseDTO> update(
            @PathVariable Long id,
            @RequestBody @Validated CategoryRequestDTO dto
    ) {
        return ResponseEntity.ok(categoryService.update(id, dto));
    }

    @Operation(summary = "Eliminar categoría", description = "Elimina una categoría por su ID.")
    @ApiResponse(responseCode = "204", description = "Categoría eliminada correctamente")
    @ApiResponse(responseCode = "404", description = "Categoría no encontrada")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        categoryService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
