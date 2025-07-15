package com.aleprimo.nova_store.entityServices.implementations;

import com.aleprimo.nova_store.controller.mappers.CategoryMapper;
import com.aleprimo.nova_store.dto.CategoryRequestDTO;
import com.aleprimo.nova_store.dto.CategoryResponseDTO;
import com.aleprimo.nova_store.models.Category;
import com.aleprimo.nova_store.persistence.CategoryDAO;
import com.aleprimo.nova_store.repository.CategoryRepository;
import com.aleprimo.nova_store.entityServices.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryDAO categoryDAO;
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public Page<CategoryResponseDTO> getAll(Pageable pageable) {
        return categoryDAO.findAllCategories(pageable).map(categoryMapper::toDto);
    }

    @Override
    public Page<CategoryResponseDTO> getActive(Pageable pageable) {
        return categoryDAO.findActiveCategories(pageable).map(categoryMapper::toDto);
    }

    @Override
    public CategoryResponseDTO getById(Long id) {
        Category category = categoryDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + id));
        return categoryMapper.toDto(category);
    }

    @Override
    public CategoryResponseDTO create(CategoryRequestDTO dto) {
        Category category = categoryMapper.toEntity(dto);
        return categoryMapper.toDto(categoryRepository.save(category));
    }

    @Override
    public CategoryResponseDTO update(Long id, CategoryRequestDTO dto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + id));
        categoryMapper.updateEntityFromDto(dto, category);
        return categoryMapper.toDto(categoryRepository.save(category));
    }
}
