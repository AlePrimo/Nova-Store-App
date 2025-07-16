package com.aleprimo.nova_store.persistence;

import com.aleprimo.nova_store.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;


public interface ProductDAO {

    Page<Product> findAllProducts(Pageable pageable);

    Page<Product> findActiveProducts(Pageable pageable);

    Page<Product> findByNameContaining(String name, Pageable pageable);

    Page<Product> findByCategoryId(Long categoryId, Pageable pageable);

    Page<Product> findByCategoryIdAndNameContaining(Long categoryId, String name, Pageable pageable);

    Optional<Product> findBySku(String sku);

    void deleteById(Long id);
}
