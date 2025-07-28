package com.aleprimo.nova_store.repository;

import com.aleprimo.nova_store.entity.Review;
import com.aleprimo.nova_store.entity.Product;
import com.aleprimo.nova_store.entity.Customer;
import com.aleprimo.nova_store.repository.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private TestEntityManager entityManager;

    private Review review;

    @BeforeEach
    void setUp() {
        Product product = Product.builder().name("Producto").build();
        Customer customer = Customer.builder().firstName("Juan").lastName("Pérez").build();

        entityManager.persist(product);
        entityManager.persist(customer);

        review = Review.builder()
                .rating(4)
                .comment("Muy bueno")
                .product(product)
                .customer(customer)
                .build();

        entityManager.persist(review);
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
