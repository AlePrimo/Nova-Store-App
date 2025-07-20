package com.aleprimo.nova_store.repository;


import com.aleprimo.nova_store.models.Customer;
import com.aleprimo.nova_store.models.Order;
import com.aleprimo.nova_store.models.OrderItem;
import com.aleprimo.nova_store.models.Product;
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
public class OrderItemRepositoryTest {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    private Order order;
    private Product product;

    @BeforeEach
    void setup() {
        Customer customer = Customer.builder()
                .firstName("María")
                .lastName("López")
                .email("maria@example.com")
                .isActive(true)
                .build();
        customerRepository.save(customer);

        product = Product.builder()
                .name("Producto A")
                .price(BigDecimal.valueOf(500))
                .stock(100)
                .isActive(true)
                .sku("SKU123")
                .build();
        productRepository.save(product);

        order = Order.builder()
                .customer(customer)
                .totalAmount(BigDecimal.valueOf(1000))
                .status("PROCESSING")
                .createdAt(LocalDateTime.now())
                .build();
        orderRepository.save(order);
    }

    @Test
    void testCreateOrderItem() {
        OrderItem item = createOrderItem();
        OrderItem saved = orderItemRepository.save(item);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getQuantity()).isEqualTo(2);
    }

    @Test
    void testReadOrderItemById() {
        OrderItem saved = orderItemRepository.save(createOrderItem());
        Optional<OrderItem> found = orderItemRepository.findById(saved.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getUnitPrice()).isEqualTo(BigDecimal.valueOf(500));
    }

    @Test
    void testUpdateOrderItem() {
        OrderItem saved = orderItemRepository.save(createOrderItem());
        saved.setQuantity(5);
        OrderItem updated = orderItemRepository.save(saved);

        assertThat(updated.getQuantity()).isEqualTo(5);
    }

    @Test
    void testDeleteOrderItem() {
        OrderItem saved = orderItemRepository.save(createOrderItem());
        orderItemRepository.delete(saved);

        Optional<OrderItem> found = orderItemRepository.findById(saved.getId());
        assertThat(found).isNotPresent();
    }

    private OrderItem createOrderItem() {
        return OrderItem.builder()
                .order(order)
                .product(product)
                .quantity(2)
                .unitPrice(product.getPrice())
                .build();
    }
}
