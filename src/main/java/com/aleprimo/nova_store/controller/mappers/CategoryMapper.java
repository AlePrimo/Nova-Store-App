package com.aleprimo.nova_store.controller.mappers;

import com.aleprimo.nova_store.dto.category.CategoryRequestDTO;
import com.aleprimo.nova_store.dto.category.CategoryResponseDTO;
import com.aleprimo.nova_store.models.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryResponseDTO toDto(Category category) {
        if (category == null) return null;

        return CategoryResponseDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .isActive(category.getIsActive())
                .createdAt(category.getCreatedAt())
                .updatedAt(category.getUpdatedAt())
                .build();
    }

    public Category toEntity(CategoryRequestDTO dto) {
        if (dto == null) return null;

        return Category.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .isActive(dto.getIsActive())
                .build();
    }

    public void updateEntityFromDto(CategoryRequestDTO dto, Category category) {
        if (dto == null || category == null) return;

        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        category.setIsActive(dto.getIsActive());
    }
}
