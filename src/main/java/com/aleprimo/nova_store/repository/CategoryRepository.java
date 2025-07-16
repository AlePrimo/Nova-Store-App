package com.aleprimo.nova_store.repository;

import com.aleprimo.nova_store.models.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Page<Category> findByIsActiveTrue(Pageable pageable);
    List<Category> findByNameContainingIgnoreCase(String name);
}