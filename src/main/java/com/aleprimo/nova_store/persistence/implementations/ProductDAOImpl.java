package com.aleprimo.nova_store.persistence.implementations;

import com.aleprimo.nova_store.models.Product;
import com.aleprimo.nova_store.persistence.ProductDAO;
import com.aleprimo.nova_store.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class ProductDAOImpl implements ProductDAO {

    private final ProductRepository productRepository;


    @Override
    public Page<Product> findAllProducts(Pageable pageable) {
        return this.productRepository.findAll(pageable);
    }

    @Override
    public Page<Product> findActiveProducts(Pageable pageable) {
        return this.productRepository.findByIsActiveTrue(pageable);
    }

    @Override
    public Page<Product> findByNameContaining(String name, Pageable pageable) {
        return this.productRepository.findByNameContainingIgnoreCase(name, pageable);
    }

    @Override
    public Page<Product> findByCategoryId(Long categoryId, Pageable pageable) {
        return this.productRepository.findByCategoryId(categoryId, pageable);
    }

    @Override
    public Page<Product> findByCategoryIdAndNameContaining(Long categoryId, String name, Pageable pageable) {
        return this.productRepository.findByCategoryIdAndNameContainingIgnoreCase(categoryId, name, pageable);
    }

    @Override
    public Optional<Product> findBySku(String sku) {
        return this.productRepository.findBySku(sku);
    }
    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }
    @Override
    public void deleteById(Long id) {
        this.productRepository.deleteById(id);
    }
}
