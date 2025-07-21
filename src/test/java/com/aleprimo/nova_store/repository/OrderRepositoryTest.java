package com.aleprimo.nova_store.repository;


import com.aleprimo.nova_store.models.Customer;
import com.aleprimo.nova_store.models.Order;
import com.aleprimo.nova_store.models.enums.OrderStatus;
import com.aleprimo.nova_store.models.enums.PaymentMethod;
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
                .orderStatus(OrderStatus.PROCESSING)
                .paymentMethod(PaymentMethod.PAYPAL)
                .build()
        );
    }

    @Test
    @DisplayName("Guardar un pedido")
    void shouldSaveOrder() {
        assertThat(order.getId()).isNotNull();
        assertThat(order.getCustomer()).isEqualTo(customer);
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.PENDING);
        assertThat(order.getPaymentMethod()).isEqualTo(PaymentMethod.CASH);
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
        order.setOrderStatus(OrderStatus.SHIPPED);
        Order updated = orderRepository.save(order);

        assertThat(updated.getOrderStatus()).isEqualTo(OrderStatus.SHIPPED);
    }

    @Test
    @DisplayName("Eliminar pedido")
    void shouldDeleteOrder() {
        orderRepository.delete(order);
        Optional<Order> deleted = orderRepository.findById(order.getId());
        assertThat(deleted).isEmpty();
    }
}
