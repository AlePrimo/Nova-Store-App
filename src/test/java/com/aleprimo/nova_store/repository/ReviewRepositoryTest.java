package com.aleprimo.nova_store.repository;


import com.aleprimo.nova_store.models.Customer;
import com.aleprimo.nova_store.models.Product;
import com.aleprimo.nova_store.models.Review;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CustomerRepository customerRepository;

    private Review review;

    @BeforeEach
    void setUp() {
        Product product = Product.builder()
                .name("Producto")
                .price(BigDecimal.valueOf(100))
                .stock(10)
                .description("Descripción del producto")
                .shortDescription("Desc corta")
                .imageUrl("imagen.jpg")
                .sku("SKU123")
                .isActive(true)
                .build();

        Customer customer = Customer.builder()
                .firstName("Juan")
                .lastName("Pérez")
                .email("juan.perez@example.com")
                .isActive(true)
                .build();

        productRepository.save(product);
        customerRepository.save(customer);

        review = Review.builder()
                .rating(4)
                .comment("Muy bueno")
                .product(product)
                .customer(customer)
                .build();

        reviewRepository.save(review);
    }

    @Test
    void testFindById() {
        Optional<Review> result = reviewRepository.findById(review.getId());

        assertTrue(result.isPresent());
        assertEquals("Muy bueno", result.get().getComment());
    }

    @Test
    void testSaveReview() {
        Review saved = reviewRepository.save(review);
        assertNotNull(saved.getId());
    }

    @Test
    void testDeleteReview() {
        reviewRepository.delete(review);
        Optional<Review> result = reviewRepository.findById(review.getId());
        assertFalse(result.isPresent());
    }

    @Test
    void testFindAll() {
        Iterable<Review> reviews = reviewRepository.findAll();
        assertTrue(reviews.iterator().hasNext());
    }
}
