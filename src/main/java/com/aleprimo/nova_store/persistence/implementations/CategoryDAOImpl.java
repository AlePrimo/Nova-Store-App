package com.aleprimo.nova_store.persistence.implementations;

import com.aleprimo.nova_store.models.Category;
import com.aleprimo.nova_store.persistence.CategoryDAO;
import com.aleprimo.nova_store.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CategoryDAOImpl implements CategoryDAO {

    private final CategoryRepository categoryRepository;

    @Override
    public Page<Category> findAllCategories(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    @Override
    public Page<Category> findActiveCategories(Pageable pageable) {
        return (Page<Category>) categoryRepository.findAll(pageable)
                .map(category -> category.getIsActive() != null && category.getIsActive() ? category : null)
                .filter(c -> c != null);
    }

    @Override
    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public Optional<Category> findByName(String name) {
        return categoryRepository.findAll().stream()
                .filter(c -> c.getName().equalsIgnoreCase(name))
                .findFirst();
    }
}
