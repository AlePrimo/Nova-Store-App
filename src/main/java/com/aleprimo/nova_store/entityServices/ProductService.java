package com.aleprimo.nova_store.entityServices;

import com.aleprimo.nova_store.dto.product.ProductRequestDTO;
import com.aleprimo.nova_store.dto.product.ProductResponseDTO;

import com.aleprimo.nova_store.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;


public interface ProductService {

    Page<ProductResponseDTO> getAllProducts(Pageable pageable);

    Page<ProductResponseDTO> getActiveProducts(Pageable pageable);

    Page<ProductResponseDTO> searchByName(String name, Pageable pageable);

    Page<ProductResponseDTO> getProductsByCategory(Long categoryId, Pageable pageable);

    Page<ProductResponseDTO> searchByCategoryAndName(Long categoryId, String name, Pageable pageable);


    ProductResponseDTO createProduct(ProductRequestDTO dto);

    ProductResponseDTO updateProduct(Long id, ProductRequestDTO dto);

    Optional<Product> getProductEntityBySku(String sku);

    Optional<ProductResponseDTO> getBySku(String sku);

    void deleteById(Long id);


}
