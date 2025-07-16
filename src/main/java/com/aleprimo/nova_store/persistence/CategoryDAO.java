package com.aleprimo.nova_store.persistence;

import com.aleprimo.nova_store.models.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CategoryDAO {

    Page<Category> findAllCategories(Pageable pageable);

    Page<Category> findActiveCategories(Pageable pageable);

    List<Category> findByNameContainingIgnoreCase(String name);

    Optional<Category> findById(Long id);

    Category save(Category category);

    void deleteById(Long id);
}
