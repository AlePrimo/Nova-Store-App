package com.aleprimo.nova_store.repository;


import com.aleprimo.nova_store.models.Customer;
import com.aleprimo.nova_store.models.Order;
import org.junit.jupiter.api.BeforeEach;
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
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    private Customer customer;

    @BeforeEach
    void setup() {
        customer = Customer.builder()
                .firstName("Juan")
                .lastName("Pérez")
                .email("juan@example.com")
                .isActive(true)
                .build();
        customerRepository.save(customer);
    }

    @Test
    void testCreateOrder() {
        Order order = createOrder();
        Order saved = orderRepository.save(order);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getCustomer()).isEqualTo(customer);
    }

    @Test
    void testReadOrderById() {
        Order saved = orderRepository.save(createOrder());
        Optional<Order> found = orderRepository.findById(saved.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getTotalAmount()).isEqualTo(BigDecimal.valueOf(1500));
    }

    @Test
    void testUpdateOrder() {
        Order saved = orderRepository.save(createOrder());
        saved.setStatus("DELIVERED");

        Order updated = orderRepository.save(saved);

        assertThat(updated.getStatus()).isEqualTo("DELIVERED");
    }

    @Test
    void testDeleteOrder() {
        Order saved = orderRepository.save(createOrder());
        orderRepository.delete(saved);

        Optional<Order> found = orderRepository.findById(saved.getId());
        assertThat(found).isNotPresent();
    }

    private Order createOrder() {
        return Order.builder()
                .customer(customer)
                .totalAmount(BigDecimal.valueOf(1500))
                .status("PENDING")
                .createdAt(LocalDateTime.now())
                .build();
    }
}
