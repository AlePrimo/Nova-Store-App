package com.aleprimo.nova_store.repository;


import com.aleprimo.nova_store.models.Category;
import com.aleprimo.nova_store.models.Customer;
import com.aleprimo.nova_store.models.Product;
import com.aleprimo.nova_store.models.Wishlist;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class WishlistRepositoryTest {

    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private Wishlist wishlist;

    @BeforeEach
    void setUp() {

        Category category = Category.builder()
                .createdAt(LocalDateTime.now())
                .description("ELECTRONICA")
                .name("TECNOLOGIA")
                .isActive(true)
                 .build();
        categoryRepository.save(category);


        Customer customer = Customer.builder()
                .firstName("Ana")
                .lastName("Pérez")
                .email("ana@example.com")
                .isActive(true)
                .build();
        customer = customerRepository.save(customer);


        Product product1 = productRepository.save(Product.builder()
                .name("Producto 1")
                .price(BigDecimal.valueOf(100))
                .stock(10)
                        .category(category)
                .sku("SKU1")

                .isActive(true)
                .build());

        Product product2 = productRepository.save(Product.builder()
                .name("Producto 2")
                .price(BigDecimal.valueOf(200))
                .stock(20)
                .sku("SKU2")
                        .category(category)
                .isActive(true)
                .build());

        wishlist = Wishlist.builder()
                .customer(customer)
                .products(List.of(product1, product2))
                .build();
        wishlist = wishlistRepository.save(wishlist);
    }

    @Test
    void testFindById() {
        Wishlist found = wishlistRepository.findById(wishlist.getId()).orElse(null);
        assertThat(found).isNotNull();
        assertThat(found.getCustomer().getEmail()).isEqualTo("ana@example.com");
    }

    @Test
    void testSave() {
        assertThat(wishlist.getId()).isNotNull();
        assertThat(wishlist.getProducts()).hasSize(2);
    }

    @Test
    void testFindAll() {
        List<Wishlist> all = wishlistRepository.findAll();
        assertThat(all).isNotEmpty();
    }
}
