package com.aleprimo.nova_store.service;

import com.aleprimo.nova_store.controller.mappers.ProductMapper;
import com.aleprimo.nova_store.dto.product.ProductRequestDTO;
import com.aleprimo.nova_store.dto.product.ProductResponseDTO;
import com.aleprimo.nova_store.entityServices.implementations.ProductServiceImpl;
import com.aleprimo.nova_store.models.Category;
import com.aleprimo.nova_store.models.Product;
import com.aleprimo.nova_store.persistence.ProductDAO;
import com.aleprimo.nova_store.repository.CategoryRepository;
import com.aleprimo.nova_store.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.*;

import java.math.BigDecimal;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ProductServiceImplTest {

    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private ProductDAO productDAO;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ProductMapper productMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllProducts_shouldReturnMappedPage() {
        Product product = Product.builder().name("Phone").build();
        ProductResponseDTO dto = new ProductResponseDTO();

        Page<Product> page = new PageImpl<>(List.of(product));
        when(productDAO.findAllProducts(any())).thenReturn(page);
        when(productMapper.toDto(product)).thenReturn(dto);

        Page<ProductResponseDTO> result = productService.getAllProducts(PageRequest.of(0, 10));

        assertThat(result.getContent()).containsExactly(dto);
    }

    @Test
    void getActiveProducts_shouldReturnMappedPage() {
        Product product = Product.builder().name("Phone").build();
        ProductResponseDTO dto = new ProductResponseDTO();

        Page<Product> page = new PageImpl<>(List.of(product));
        when(productDAO.findActiveProducts(any())).thenReturn(page);
        when(productMapper.toDto(product)).thenReturn(dto);

        Page<ProductResponseDTO> result = productService.getActiveProducts(PageRequest.of(0, 10));

        assertThat(result.getContent()).containsExactly(dto);
    }

    @Test
    void searchByName_shouldReturnMappedPage() {
        Product product = Product.builder().name("Phone").build();
        ProductResponseDTO dto = new ProductResponseDTO();

        Page<Product> page = new PageImpl<>(List.of(product));
        when(productDAO.findByNameContaining(eq("phone"), any())).thenReturn(page);
        when(productMapper.toDto(product)).thenReturn(dto);

        Page<ProductResponseDTO> result = productService.searchByName("phone", PageRequest.of(0, 10));

        assertThat(result.getContent()).containsExactly(dto);
    }

    @Test
    void getProductsByCategory_shouldReturnMappedPage() {
        Product product = Product.builder().name("TV").build();
        ProductResponseDTO dto = new ProductResponseDTO();

        Page<Product> page = new PageImpl<>(List.of(product));
        when(productDAO.findByCategoryId(eq(1L), any())).thenReturn(page);
        when(productMapper.toDto(product)).thenReturn(dto);

        Page<ProductResponseDTO> result = productService.getProductsByCategory(1L, PageRequest.of(0, 10));

        assertThat(result.getContent()).containsExactly(dto);
    }

    @Test
    void searchByCategoryAndName_shouldReturnMappedPage() {
        Product product = Product.builder().name("TV").build();
        ProductResponseDTO dto = new ProductResponseDTO();

        Page<Product> page = new PageImpl<>(List.of(product));
        when(productDAO.findByCategoryIdAndNameContaining(eq(1L), eq("tv"), any())).thenReturn(page);
        when(productMapper.toDto(product)).thenReturn(dto);

        Page<ProductResponseDTO> result = productService.searchByCategoryAndName(1L, "tv", PageRequest.of(0, 10));

        assertThat(result.getContent()).containsExactly(dto);
    }

    @Test
    void getProductEntityBySku_shouldReturnProduct() {
        Product product = Product.builder().sku("SKU123").build();
        when(productDAO.findBySku("SKU123")).thenReturn(Optional.of(product));

        Optional<Product> result = productService.getProductEntityBySku("SKU123");

        assertThat(result).contains(product);
    }

    @Test
    void getBySku_shouldReturnDTO() {
        Product product = Product.builder().sku("SKU123").build();
        ProductResponseDTO dto = new ProductResponseDTO();

        when(productDAO.findBySku("SKU123")).thenReturn(Optional.of(product));
        when(productMapper.toDto(product)).thenReturn(dto);

        Optional<ProductResponseDTO> result = productService.getBySku("SKU123");

        assertThat(result).contains(dto);
    }

    @Test
    void createProduct_shouldSaveAndReturnDTO() {
        ProductRequestDTO request = new ProductRequestDTO();
        request.setCategoryId(1L);

        Category category = new Category();
        Product entity = Product.builder().name("Product").build();
        ProductResponseDTO dto = new ProductResponseDTO();

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(productMapper.toEntity(request)).thenReturn(entity);
        when(productRepository.save(entity)).thenReturn(entity);
        when(productMapper.toDto(entity)).thenReturn(dto);

        ProductResponseDTO result = productService.createProduct(request);

        assertThat(result).isEqualTo(dto);
    }

    @Test
    void createProduct_shouldThrowIfCategoryMissing() {
        ProductRequestDTO request = new ProductRequestDTO();
        request.setCategoryId(99L);
        when(categoryRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> productService.createProduct(request));
    }

    @Test
    void updateProduct_shouldUpdateAndReturnDTO() {
        ProductRequestDTO request = new ProductRequestDTO();
        request.setCategoryId(1L);

        Product product = Product.builder().id(1L).build();
        Category category = new Category();
        ProductResponseDTO dto = new ProductResponseDTO();

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        doNothing().when(productMapper).updateEntityFromDto(request, product);
        when(productRepository.save(product)).thenReturn(product);
        when(productMapper.toDto(product)).thenReturn(dto);

        ProductResponseDTO result = productService.updateProduct(1L, request);

        assertThat(result).isEqualTo(dto);
    }

    @Test
    void updateProduct_shouldThrowIfProductNotFound() {
        ProductRequestDTO request = new ProductRequestDTO();
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> productService.updateProduct(1L, request));
    }

    @Test
    void updateProduct_shouldThrowIfCategoryNotFound() {
        ProductRequestDTO request = new ProductRequestDTO();
        request.setCategoryId(5L);
        Product product = Product.builder().id(1L).build();

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(categoryRepository.findById(5L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> productService.updateProduct(1L, request));
    }

    @Test
    void deleteById_shouldCallDaoDelete() {
        doNothing().when(productDAO).deleteById(1L);
        productService.deleteById(1L);
        verify(productDAO).deleteById(1L);
    }
}
