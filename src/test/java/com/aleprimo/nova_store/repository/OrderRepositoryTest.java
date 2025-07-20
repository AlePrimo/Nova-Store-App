package com.aleprimo.nova_store.repository;


import com.aleprimo.nova_store.models.Customer;
import com.aleprimo.nova_store.models.Order;
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
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    private Customer customer;
    private Order order;

    @BeforeEach
    void setUp() {
        customer = customerRepository.save(
            Customer.builder()
                .firstName("Juan")
                .lastName("Pérez")
                .email("juan@example.com")
                .isActive(true)
                .build()
        );

        order = orderRepository.save(
            Order.builder()
                .customer(customer)
                .createdAt(LocalDateTime.now())
                .totalAmount(BigDecimal.valueOf(120.50))
                .status("PROCESSING")
                .paymentMethod("CREDIT_CARD")
                .build()
        );
    }

    @Test
    @DisplayName("Guardar un pedido")
    void shouldSaveOrder() {
        assertThat(order.getId()).isNotNull();
        assertThat(order.getCustomer()).isEqualTo(customer);
        assertThat(order.getStatus()).isEqualTo("PROCESSING");
        assertThat(order.getPaymentMethod()).isEqualTo("CREDIT_CARD");
    }

    @Test
    @DisplayName("Buscar pedido por ID")
    void shouldFindOrderById() {
        Optional<Order> found = orderRepository.findById(order.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getTotalAmount()).isEqualTo(order.getTotalAmount());
    }

    @Test
    @DisplayName("Actualizar pedido")
    void shouldUpdateOrder() {
        order.setStatus("COMPLETED");
        Order updated = orderRepository.save(order);

        assertThat(updated.getStatus()).isEqualTo("COMPLETED");
    }

    @Test
    @DisplayName("Eliminar pedido")
    void shouldDeleteOrder() {
        orderRepository.delete(order);
        Optional<Order> deleted = orderRepository.findById(order.getId());
        assertThat(deleted).isEmpty();
    }
}
