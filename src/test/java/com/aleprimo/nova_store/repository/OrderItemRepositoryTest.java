package com.aleprimo.nova_store.repository;


import com.aleprimo.nova_store.models.*;
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
class OrderItemRepositoryTest {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private Order order;
    private Product product;
    private OrderItem orderItem;

    @BeforeEach
    void setUp() {
        Category category = categoryRepository.save(
                Category.builder()
                        .name("Tecnología")
                        .description("Productos tecnológicos")
                        .isActive(true)
                        .build()
        );

        Customer customer = customerRepository.save(
                Customer.builder()
                        .firstName("María")
                        .lastName("Gómez")
                        .email("maria@example.com")
                        .isActive(true)
                        .build()
        );

        product = productRepository.save(
                Product.builder()
                        .name("Laptop")
                        .description("Una laptop potente")
                        .shortDescription("Gaming Laptop")
                        .price(BigDecimal.valueOf(999.99))
                        .stock(10)
                        .sku("SKU-123")
                        .category(category)
                        .isActive(true)
                        .build()
        );

        order = orderRepository.save(
                Order.builder()
                        .customer(customer)
                        .createdAt(LocalDateTime.now())
                        .orderStatus(OrderStatus.PENDING)
                        .totalAmount(BigDecimal.ZERO)
                        .paymentMethod(PaymentMethod.CASH)
                        .build()
        );

        orderItem = orderItemRepository.save(
                OrderItem.builder()
                        .order(order)
                        .product(product)
                        .quantity(2)
                        .unitPrice(BigDecimal.valueOf(1999.98))
                        .build()
        );
    }


    @Test
    @DisplayName("Guardar item de pedido")
    void shouldSaveOrderItem() {
        assertThat(orderItem.getId()).isNotNull();
        assertThat(orderItem.getProduct()).isEqualTo(product);
        assertThat(orderItem.getQuantity()).isEqualTo(2);
    }

    @Test
    @DisplayName("Buscar item por ID")
    void shouldFindOrderItemById() {
        Optional<OrderItem> found = orderItemRepository.findById(orderItem.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getUnitPrice()).isEqualTo(orderItem.getUnitPrice());
    }

    @Test
    @DisplayName("Actualizar item de pedido")
    void shouldUpdateOrderItem() {
        orderItem.setQuantity(3);
        orderItem.setUnitPrice(product.getPrice().multiply(BigDecimal.valueOf(3)));
        OrderItem updated = orderItemRepository.save(orderItem);

        assertThat(updated.getQuantity()).isEqualTo(3);
        assertThat(updated.getUnitPrice()).isEqualTo(BigDecimal.valueOf(2999.97));
    }

    @Test
    @DisplayName("Eliminar item de pedido")
    void shouldDeleteOrderItem() {
        orderItemRepository.delete(orderItem);
        Optional<OrderItem> deleted = orderItemRepository.findById(orderItem.getId());
        assertThat(deleted).isEmpty();
    }
}
