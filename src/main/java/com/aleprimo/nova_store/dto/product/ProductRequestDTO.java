package com.aleprimo.nova_store.dto.product;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "DTO de solicitud para crear o actualizar productos")
public class ProductRequestDTO {

    @NotBlank(message = "El nombre del producto es obligatorio")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    @Schema(description = "Nombre del producto", example = "Smartphone Samsung Galaxy S22")
    String name;

    @NotBlank(message = "La descripción del producto es obligatoria")
    @Schema(description = "Descripción larga del producto", example = "Pantalla AMOLED de 6.1 pulgadas, cámara triple...")
    String description;

    @NotBlank(message = "La descripción corta es obligatoria")
    @Size(max = 255, message = "La descripción corta no puede superar los 255 caracteres")
    @Schema(description = "Descripción corta del producto", example = "Samsung Galaxy S22 con 128GB")
    String shortDescription;

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor a 0")
    @Digits(integer = 10, fraction = 2, message = "El precio debe tener hasta 2 decimales")
    @Schema(description = "Precio del producto", example = "259999.99")
    BigDecimal price;

    @NotNull(message = "El stock es obligatorio")
    @Min(value = 0, message = "El stock no puede ser negativo")
    @Schema(description = "Cantidad disponible en stock", example = "50")
    Integer stock;

    @NotBlank(message = "La URL de imagen es obligatoria")
    @Schema(description = "URL de la imagen del producto", example = "https://cdn.novastore.com/productos/samsung-s22.jpg")
    String imageUrl;

    @NotBlank(message = "El SKU es obligatorio")
    @Schema(description = "SKU del producto (código único)", example = "SAM-S22-128GB-BLK")
    String sku;

    @NotNull(message = "El estado de activación es obligatorio")
    @Schema(description = "Indica si el producto está activo", example = "true")
    Boolean isActive;

    @NotNull(message = "Debe especificar la categoría del producto")
    @Schema(description = "ID de la categoría a la que pertenece el producto", example = "5")
    Long categoryId;
}
