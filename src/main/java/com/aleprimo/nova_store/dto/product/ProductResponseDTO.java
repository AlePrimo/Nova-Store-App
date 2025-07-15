package com.aleprimo.nova_store.dto.product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "DTO de respuesta para Producto")
public class ProductResponseDTO {

    @Schema(description = "ID del producto", example = "101")
    Long id;

    @Schema(description = "Nombre del producto", example = "Smartphone Samsung Galaxy S22")
    String name;

    @Schema(description = "Descripción larga del producto", example = "Este smartphone cuenta con una pantalla AMOLED de 6.1 pulgadas...")
    String description;

    @Schema(description = "Descripción corta del producto", example = "Samsung Galaxy S22 con 128GB y cámara triple.")
    String shortDescription;

    @Schema(description = "Precio del producto", example = "259999.99")
    BigDecimal price;

    @Schema(description = "Cantidad de stock disponible", example = "150")
    Integer stock;

    @Schema(description = "URL de la imagen del producto", example = "https://cdn.novastore.com/productos/samsung-s22.jpg")
    String imageUrl;

    @Schema(description = "SKU único del producto", example = "SAM-S22-128GB-BLK")
    String sku;

    @Schema(description = "Indica si el producto está activo", example = "true")
    Boolean isActive;

    @Schema(description = "Nombre de la categoría", example = "Electrónica")
    String categoryName;

    @Schema(description = "ID de la categoría", example = "5")
    Long categoryId;

    @Schema(description = "Fecha de creación del producto", example = "2025-07-01T10:00:00")
    LocalDateTime createdAt;

    @Schema(description = "Fecha de última modificación", example = "2025-07-10T16:30:00")
    LocalDateTime updatedAt;
}
