package com.aleprimo.nova_store.entityServices.implementations;
import com.aleprimo.nova_store.controller.mappers.ProductMapper;
import com.aleprimo.nova_store.dto.product.ProductRequestDTO;
import com.aleprimo.nova_store.dto.product.ProductResponseDTO;
import com.aleprimo.nova_store.models.Product;
import com.aleprimo.nova_store.persistence.ProductDAO;
import com.aleprimo.nova_store.repository.CategoryRepository;
import com.aleprimo.nova_store.repository.ProductRepository;
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
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    @Override
    public Page<ProductResponseDTO> getAllProducts(Pageable pageable) {
        return productDAO.findAllProducts(pageable)
                .map(productMapper::toDto);
    }

    @Override
    public Page<ProductResponseDTO> getActiveProducts(Pageable pageable) {
        return productDAO.findActiveProducts(pageable)
                .map(productMapper::toDto);
    }

    @Override
    public Page<ProductResponseDTO> searchByName(String name, Pageable pageable) {
        return productDAO.findByNameContaining(name, pageable)
                .map(productMapper::toDto);
    }

    @Override
    public Page<ProductResponseDTO> getProductsByCategory(Long categoryId, Pageable pageable) {
        return productDAO.findByCategoryId(categoryId, pageable)
                .map(productMapper::toDto);
    }

    @Override
    public Page<ProductResponseDTO> searchByCategoryAndName(Long categoryId, String name, Pageable pageable) {
        return productDAO.findByCategoryIdAndNameContaining(categoryId, name, pageable)
                .map(productMapper::toDto);
    }

    @Override
    public ProductResponseDTO getBySku(String sku) {
        Product product = productDAO.findBySku(sku)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con SKU: " + sku));
        return productMapper.toDto(product);
    }

    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO dto) {
        // Validación opcional de existencia de categoría
        categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        Product product = productMapper.toEntity(dto);
        Product saved = productRepository.save(product);
        return productMapper.toDto(saved);
    }

    @Override
    public ProductResponseDTO updateProduct(Long id, ProductRequestDTO dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));

        // Validación opcional de categoría
        categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        productMapper.updateEntityFromDto(dto, product);
        Product updated = productRepository.save(product);
        return productMapper.toDto(updated);
    }
}
