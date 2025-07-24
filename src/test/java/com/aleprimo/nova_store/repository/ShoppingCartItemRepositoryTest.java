package com.aleprimo.nova_store.repository;


import com.aleprimo.nova_store.models.*;
import jakarta.transaction.Transactional;
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
@Transactional
public class ShoppingCartItemRepositoryTest {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ShoppingCartItemRepository shoppingCartItemRepository;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CustomerRepository customerRepository;

    private ShoppingCart cart;
    private Product product;

    @BeforeEach
    void setUp() {
        Customer customer = Customer.builder()
                .firstName("Juan")
                .lastName("Pérez")
                .email("juan@example.com")
                .isActive(true)
                .createdAt(LocalDateTime.now())
                .build();

        customer = customerRepository.save(customer);

        cart = ShoppingCart.builder()
                .customer(customer)
                .totalPrice(BigDecimal.ZERO)
                .build();

        cart = shoppingCartRepository.save(cart);


        Category category = Category.builder()
                .name("Categoría de prueba")
                .description("Descripción de prueba")
                .isActive(true)
                .createdAt(LocalDateTime.now())
                .build();

        category = categoryRepository.save(category);

        product = Product.builder()
                .name("Producto de prueba")
                .price(BigDecimal.valueOf(100))
                .stock(10)
                .sku("SKU123")
                .isActive(true)
                .category(category)
                .build();

        product = productRepository.save(product);
    }


    @Test
    void testSaveAndFindByShoppingCartId() {
        ShoppingCartItem item = ShoppingCartItem.builder()
                .shoppingCart(cart)
                .price(BigDecimal.valueOf(100))
                .product(product)
                .quantity(2)
                .build();

        shoppingCartItemRepository.save(item);

        List<ShoppingCartItem> items = shoppingCartItemRepository.findByShoppingCartId(cart.getId());

        assertThat(items).hasSize(1);
        assertThat(items.get(0).getQuantity()).isEqualTo(2);
        assertThat(items.get(0).getProduct().getId()).isEqualTo(product.getId());
    }

    @Test
    void testDeleteByShoppingCartId() {
        ShoppingCartItem item = ShoppingCartItem.builder()
                .shoppingCart(cart)
                .price(BigDecimal.valueOf(100))
                .product(product)
                .quantity(2)
                .build();

        shoppingCartItemRepository.save(item);

        shoppingCartItemRepository.deleteByShoppingCartId(cart.getId());

        List<ShoppingCartItem> items = shoppingCartItemRepository.findByShoppingCartId(cart.getId());
        assertThat(items).isEmpty();
    }

    @Test
    void testExistsByShoppingCartIdAndProductId() {
        ShoppingCartItem item = ShoppingCartItem.builder()
                .shoppingCart(cart)
                .price(BigDecimal.valueOf(100))
                .product(product)
                .quantity(1)
                .build();

        shoppingCartItemRepository.save(item);

        boolean exists = shoppingCartItemRepository.existsByShoppingCartIdAndProductId(cart.getId(), product.getId());
        assertThat(exists).isTrue();
    }
}
