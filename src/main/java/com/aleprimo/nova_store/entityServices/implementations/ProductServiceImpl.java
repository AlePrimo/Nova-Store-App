package com.aleprimo.nova_store.entityServices.implementations;

import com.aleprimo.nova_store.models.Product;
import com.aleprimo.nova_store.persistence.ProductDAO;
import com.aleprimo.nova_store.entityServices.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductDAO productDAO;

    @Override
    public Page<Product> getAllProducts(Pageable pageable) {
        return productDAO.findAllProducts(pageable);
    }

    @Override
    public Page<Product> getActiveProducts(Pageable pageable) {
        return productDAO.findActiveProducts(pageable);
    }

    @Override
    public Page<Product> searchByName(String name, Pageable pageable) {
        return productDAO.findByNameContaining(name, pageable);
    }

    @Override
    public Page<Product> getProductsByCategory(Long categoryId, Pageable pageable) {
        return productDAO.findByCategoryId(categoryId, pageable);
    }

    @Override
    public Page<Product> searchByCategoryAndName(Long categoryId, String name, Pageable pageable) {
        return productDAO.findByCategoryIdAndNameContaining(categoryId, name, pageable);
    }

    @Override
    public Optional<Product> getBySku(String sku) {
        return productDAO.findBySku(sku);
    }
}
