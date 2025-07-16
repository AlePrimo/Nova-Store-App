package com.aleprimo.nova_store.entityServices;


import com.aleprimo.nova_store.dto.category.CategoryRequestDTO;
import com.aleprimo.nova_store.dto.category.CategoryResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {

    Page<CategoryResponseDTO> getAll(Pageable pageable);

    Page<CategoryResponseDTO> getActive(Pageable pageable);

    CategoryResponseDTO getById(Long id);

    List<CategoryResponseDTO> searchByName(String name);

    CategoryResponseDTO create(CategoryRequestDTO dto);

    CategoryResponseDTO update(Long id, CategoryRequestDTO dto);

    void deleteById(Long id);
}
