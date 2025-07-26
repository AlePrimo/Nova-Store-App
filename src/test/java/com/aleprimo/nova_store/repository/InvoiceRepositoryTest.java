package com.aleprimo.nova_store.repository;


import com.aleprimo.nova_store.models.Customer;
import com.aleprimo.nova_store.models.Invoice;
import com.aleprimo.nova_store.models.Order;
import com.aleprimo.nova_store.models.enums.OrderStatus;
import com.aleprimo.nova_store.models.enums.PaymentMethod;
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
class InvoiceRepositoryTest {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    private Order order;

    @BeforeEach
    void setUp() {

        Customer customer = new Customer();
        customer.setFirstName("Test");
        customer.setLastName("User");
        customer.setEmail("test@example.com");
        customer.setIsActive(true);
        customer.setCreatedAt(LocalDateTime.now());

        customer = customerRepository.save(customer);


        order = new Order();
        order.setCustomer(customer);
        order.setTotalAmount(BigDecimal.valueOf(100));
        order.setCreatedAt(LocalDateTime.now());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setPaymentMethod(PaymentMethod.CASH);
        orderRepository.save(order);
    }

    @Test
    void testSaveInvoice() {
        Invoice invoice = new Invoice();
        invoice.setOrder(order);
        invoice.setPaymentMethod(PaymentMethod.CASH);
        invoice.setIssuedAt(LocalDateTime.now());
        invoice.setTotalAmount(BigDecimal.valueOf(100));

        Invoice saved = invoiceRepository.save(invoice);

        assertThat(saved.getId()).isNotNull();

    }

    @Test
    void testFindById() {
        Invoice invoice = new Invoice();
        invoice.setOrder(order);
        invoice.setPaymentMethod(PaymentMethod.CASH);
        invoice.setIssuedAt(LocalDateTime.now());
        invoice.setTotalAmount(BigDecimal.valueOf(200));
        invoice = invoiceRepository.save(invoice);

        Optional<Invoice> found = invoiceRepository.findById(invoice.getId());

        assertThat(found).isPresent();

    }
}
