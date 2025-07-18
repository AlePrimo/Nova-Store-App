package com.aleprimo.nova_store.repository;

import com.aleprimo.nova_store.models.Category;
import com.aleprimo.nova_store.models.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.*;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;

@DataJpaTest
@EntityScan(basePackages = "com.aleprimo.nova_store.models")

class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private Category category;

    private Product product1;
    private Product product2;

    @BeforeEach
    void setUp() {
        category = Category.builder()
                .name("Electronics")
                .description("All electronic items")
                .build();

        category = categoryRepository.save(category);

        product1 = Product.builder()
                .name("Smartphone")
                .description("Latest smartphone")
                .shortDescription("New phone")
                .price(BigDecimal.valueOf(799.99))
                .stock(20)
                .imageUrl("http://image.url/smartphone")
                .sku("SKU123")
                .isActive(true)
                .category(category)
                .build();

        product2 = Product.builder()
                .name("Smart TV")
                .description("50 inch Smart TV")
                .shortDescription("TV")
                .price(BigDecimal.valueOf(999.99))
                .stock(10)
                .imageUrl("http://image.url/tv")
                .sku("SKU456")
                .isActive(true)
                .category(category)
                .build();

        productRepository.save(product1);
        productRepository.save(product2);
        productRepository.flush();
    }

    @Test
    void shouldFindByIsActiveTrue() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Product> result = productRepository.findByIsActiveTrue(pageable);
        assertThat(result.getContent()).hasSize(2);
    }

    @Test
    void shouldFindByCategoryId() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Product> result = productRepository.findByCategoryId(category.getId(), pageable);
        assertThat(result.getContent()).hasSize(2);
    }

    @Test
    void shouldFindByNameContainingIgnoreCase() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Product> result = productRepository.findByNameContainingIgnoreCase("smart", pageable);
        assertThat(result.getContent()).hasSize(2);
    }

    @Test
    void shouldFindByCategoryIdAndNameContainingIgnoreCase() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Product> result = productRepository.findByCategoryIdAndNameContainingIgnoreCase(category.getId(), "tv", pageable);
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getName()).isEqualTo("Smart TV");
    }

    @Test
    void shouldFindBySku() {
        Optional<Product> result = productRepository.findBySku("SKU123");
        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("Smartphone");
    }

}
