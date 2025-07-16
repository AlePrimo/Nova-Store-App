package com.aleprimo.nova_store.entityServices.implementations;

import com.aleprimo.nova_store.controller.mappers.CategoryMapper;
import com.aleprimo.nova_store.dto.category.CategoryRequestDTO;
import com.aleprimo.nova_store.dto.category.CategoryResponseDTO;
import com.aleprimo.nova_store.entityServices.CategoryService;
import com.aleprimo.nova_store.models.Category;
import com.aleprimo.nova_store.persistence.CategoryDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryDAO categoryDAO;
    private final CategoryMapper categoryMapper;

    @Override
    public Page<CategoryResponseDTO> getAll(Pageable pageable) {
        return categoryDAO.findAllCategories(pageable)
                .map(categoryMapper::toDto);
    }

    @Override
    public Page<CategoryResponseDTO> getActive(Pageable pageable) {
        return categoryDAO.findActiveCategories(pageable)
                .map(categoryMapper::toDto);
    }

    @Override
    public CategoryResponseDTO getById(Long id) {
        return categoryDAO.findById(id)
                .map(categoryMapper::toDto)
                .orElseThrow(() -> new NoSuchElementException("Categoría no encontrada con ID: " + id));
    }

    @Override
    public List<CategoryResponseDTO> searchByName(String name) {
        return categoryDAO.findByNameContainingIgnoreCase(name).stream()
                .map(categoryMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryResponseDTO create(CategoryRequestDTO dto) {
        Category category = categoryMapper.toEntity(dto);
        return categoryMapper.toDto(categoryDAO.save(category));
    }

    @Override
    public CategoryResponseDTO update(Long id, CategoryRequestDTO dto) {
        Category category = categoryDAO.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Categoría no encontrada con ID: " + id));

        categoryMapper.updateEntityFromDto(dto, category);
        return categoryMapper.toDto(categoryDAO.save(category));
    }

    @Override
    public void deleteById(Long id) {
        if (categoryDAO.findById(id).isEmpty()) {
            throw new NoSuchElementException("No se puede eliminar. Categoría no encontrada con ID: " + id);
        }
        categoryDAO.deleteById(id);
    }
}