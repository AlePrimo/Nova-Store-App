package com.aleprimo.nova_store.repository;

import com.aleprimo.nova_store.models.Customer;
import com.aleprimo.nova_store.models.Order;
import com.aleprimo.nova_store.models.Payment;
import com.aleprimo.nova_store.models.enums.OrderStatus;
import com.aleprimo.nova_store.models.enums.PaymentMethod;
import com.aleprimo.nova_store.models.enums.PaymentStatus;
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

@ActiveProfiles("test")
@DataJpaTest
class PaymentRepositoryTest {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderRepository orderRepository;

    private Order order;

    @BeforeEach
    void setUp() {
        Customer customer = Customer.builder()
                .firstName("Juan")
                .lastName("Pérez")
                .email("juan@mail.com")
                .isActive(true)
                .build();

        customerRepository.save(customer);

        order = Order.builder()
                .customer(customer)
                .totalAmount(new BigDecimal("100.00"))
                .paymentMethod(PaymentMethod.CREDIT_CARD)
                .orderStatus(OrderStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();

        orderRepository.save(order);
    }

    @Test
    void testSaveAndFindById() {
        Payment payment = Payment.builder()
                .order(order)
                .amount(new BigDecimal("100.00"))
                .paymentMethod(PaymentMethod.CREDIT_CARD)
                .paymentStatus(PaymentStatus.COMPLETED)
                .build();

        Payment saved = paymentRepository.save(payment);

        Optional<Payment> found = paymentRepository.findById(saved.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getAmount()).isEqualTo(new BigDecimal("100.00"));
        assertThat(found.get().getOrder().getId()).isEqualTo(order.getId());
    }

    @Test
    void testFindAll() {
        Payment payment = Payment.builder()
                .order(order)
                .amount(new BigDecimal("100.00"))
                .paymentMethod(PaymentMethod.CREDIT_CARD)
                .paymentStatus(PaymentStatus.COMPLETED)
                .build();

        paymentRepository.save(payment);

        List<Payment> result = paymentRepository.findAll();

        assertThat(result).hasSize(1);
    }

    @Test
    void testDelete() {
        Payment payment = Payment.builder()
                .order(order)
                .amount(new BigDecimal("100.00"))
                .paymentMethod(PaymentMethod.CREDIT_CARD)
                .paymentStatus(PaymentStatus.COMPLETED)
                .build();

        Payment saved = paymentRepository.save(payment);
        paymentRepository.delete(saved);

        Optional<Payment> found = paymentRepository.findById(saved.getId());

        assertThat(found).isEmpty();
    }
}