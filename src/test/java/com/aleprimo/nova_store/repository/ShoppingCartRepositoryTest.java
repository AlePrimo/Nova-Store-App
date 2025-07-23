package com.aleprimo.nova_store.repository;


import com.aleprimo.nova_store.models.Customer;
import com.aleprimo.nova_store.models.ShoppingCart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class ShoppingCartRepositoryTest {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private CustomerRepository customerRepository;

    private Customer customer;

    @BeforeEach
    void setUp() {
        customer = Customer.builder()
                .firstName("Juan")
                .lastName("Pérez")
                .email("juan@example.com")
                .isActive(true)
                .createdAt(LocalDateTime.now())
                .build();

        customer = customerRepository.save(customer);
    }

    @Test
    @DisplayName("Guardar carrito con cliente asociado")
    void saveShoppingCart() {
        ShoppingCart cart = ShoppingCart.builder()
                .customer(customer)
                .totalPrice(BigDecimal.valueOf(100.00))
                .build();

        ShoppingCart saved = shoppingCartRepository.save(cart);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getCustomer().getId()).isEqualTo(customer.getId());
        assertThat(saved.getTotalPrice()).isEqualTo(BigDecimal.valueOf(100.00));
    }

    @Test
    @DisplayName("Buscar carrito por ID de cliente")
    void findByCustomerId() {
        ShoppingCart cart = ShoppingCart.builder()
                .customer(customer)
                .totalPrice(BigDecimal.valueOf(50.00))
                .build();

        shoppingCartRepository.save(cart);

        Optional<ShoppingCart> result = shoppingCartRepository.findByCustomerId(customer.getId());

        assertThat(result).isPresent();
        assertThat(result.get().getCustomer().getId()).isEqualTo(customer.getId());
    }

    @Test
    @DisplayName("Verificar existencia de carrito por ID de cliente")
    void existsByCustomerId() {
        ShoppingCart cart = ShoppingCart.builder()
                .customer(customer)
                .totalPrice(BigDecimal.valueOf(75.00))
                .build();

        shoppingCartRepository.save(cart);

        boolean exists = shoppingCartRepository.existsByCustomerId(customer.getId());

        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("Eliminar carrito")
    void deleteShoppingCart() {
        ShoppingCart cart = ShoppingCart.builder()
                .customer(customer)
                .totalPrice(BigDecimal.valueOf(60.00))
                .build();

        ShoppingCart saved = shoppingCartRepository.save(cart);
        Long cartId = saved.getId();

        shoppingCartRepository.delete(saved);

        Optional<ShoppingCart> result = shoppingCartRepository.findById(cartId);

        assertThat(result).isNotPresent();
    }
}
