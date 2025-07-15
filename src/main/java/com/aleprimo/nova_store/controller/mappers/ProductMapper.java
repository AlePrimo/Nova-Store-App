package com.aleprimo.nova_store.controller.mappers;

import com.aleprimo.nova_store.dto.ProductRequestDTO;
import com.aleprimo.nova_store.dto.ProductResponseDTO;
import com.aleprimo.nova_store.models.Category;
import com.aleprimo.nova_store.models.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductResponseDTO toDto(Product product) {
        if (product == null) return null;

        return ProductResponseDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .shortDescription(product.getShortDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .imageUrl(product.getImageUrl())
                .sku(product.getSku())
                .isActive(product.getIsActive())
                .categoryId(product.getCategory() != null ? product.getCategory().getId() : null)
                .categoryName(product.getCategory() != null ? product.getCategory().getName() : null)
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }

    public Product toEntity(ProductRequestDTO dto) {
        if (dto == null) return null;

        return Product.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .shortDescription(dto.getShortDescription())
                .price(dto.getPrice())
                .stock(dto.getStock())
                .imageUrl(dto.getImageUrl())
                .sku(dto.getSku())
                .isActive(dto.getIsActive())
                .category(Category.builder().id(dto.getCategoryId()).build()) // solo seteás el ID
                .build();
    }

    public void updateEntityFromDto(ProductRequestDTO dto, Product product) {
        if (dto == null || product == null) return;

        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setShortDescription(dto.getShortDescription());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());
        product.setImageUrl(dto.getImageUrl());
        product.setSku(dto.getSku());
        product.setIsActive(dto.getIsActive());
        product.setCategory(Category.builder().id(dto.getCategoryId()).build());
    }
}
