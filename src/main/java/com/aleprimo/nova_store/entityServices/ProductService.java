package com.aleprimo.nova_store.entityServices;

import com.aleprimo.nova_store.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProductService {

    Page<Product> getAllProducts(Pageable pageable);

    Page<Product> getActiveProducts(Pageable pageable);

    Page<Product> searchByName(String name, Pageable pageable);

    Page<Product> getProductsByCategory(Long categoryId, Pageable pageable);

    Page<Product> searchByCategoryAndName(Long categoryId, String name, Pageable pageable);

    Optional<Product> getBySku(String sku);
}
