package com.aleprimo.nova_store.repository;


import com.aleprimo.nova_store.models.Customer;
import com.aleprimo.nova_store.models.Order;
import com.aleprimo.nova_store.models.Shipping;
import com.aleprimo.nova_store.models.enums.OrderStatus;
import com.aleprimo.nova_store.models.enums.PaymentMethod;
import com.aleprimo.nova_store.models.enums.ShippingStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class ShippingRepositoryTest {

    @Autowired
    private ShippingRepository shippingRepository;

    @Autowired
    private OrderRepository orderRepository;
   @Autowired
    private CustomerRepository  customerRepository;
    private Order order;
    private Customer customer;

    @BeforeEach
    void setUp() {

        customer = Customer.builder()
                .firstName("pepe")
                .lastName("argento")
                .email("pepe@gmail.com")
                .isActive(true)
                .createdAt(LocalDateTime.now())
                .build();
customerRepository.save(customer);


        order = Order.builder()
                .paymentMethod(PaymentMethod.CASH)
                .orderStatus(OrderStatus.SHIPPED)
                .customer(customer)
                .createdAt(LocalDateTime.now())
                .totalAmount(BigDecimal.valueOf(100)).build();
        orderRepository.save(order);

        Shipping shipping = Shipping.builder()
                .order(order)
                .address("Calle Falsa 123")
                .shippedAt(LocalDateTime.now())
                .deliveredAt(LocalDateTime.now().plusDays(3))
                .status(ShippingStatus.PENDING)
                .city("panama")
                .country("panama")
                .postalCode("14996")
                .build();

        shippingRepository.save(shipping);
    }

    @Test
    void testFindById() {
        List<Shipping> shippings = shippingRepository.findAll();
        Shipping shipping = shippings.get(0);

        Optional<Shipping> found = shippingRepository.findById(shipping.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getAddress()).isEqualTo("Calle Falsa 123");
    }

    @Test
    void testFindAll() {
        List<Shipping> result = shippingRepository.findAll();
        assertThat(result).hasSize(1);
    }

    @Test
    void testSave() {
        Shipping newShipping = Shipping.builder()
                .order(order)
                .address("Otra calle 456")
                .shippedAt(LocalDateTime.now())
                .deliveredAt(LocalDateTime.now().plusDays(5))
                .city("panama")
                .postalCode("1496")
                .country("panama")
                .status(ShippingStatus.SHIPPED)
                .build();

        Shipping saved = shippingRepository.save(newShipping);
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getAddress()).isEqualTo("Otra calle 456");
    }

    @Test
    void testDelete() {
        Shipping shipping = shippingRepository.findAll().get(0);
        shippingRepository.delete(shipping);

        Optional<Shipping> deleted = shippingRepository.findById(shipping.getId());
        assertThat(deleted).isEmpty();
    }
}
