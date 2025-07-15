package com.aleprimo.nova_store.entityServices;

import com.aleprimo.nova_store.dto.category.CategoryRequestDTO;
import com.aleprimo.nova_store.dto.category.CategoryResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    Page<CategoryResponseDTO> getAll(Pageable pageable);
    Page<CategoryResponseDTO> getActive(Pageable pageable);
    CategoryResponseDTO getById(Long id);
    CategoryResponseDTO create(CategoryRequestDTO dto);
    CategoryResponseDTO update(Long id, CategoryRequestDTO dto);
}
