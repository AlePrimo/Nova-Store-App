package com.aleprimo.nova_store.service;


import com.aleprimo.nova_store.entityServices.implementations.PurchaseHistoryServiceImpl;
import com.aleprimo.nova_store.models.Customer;
import com.aleprimo.nova_store.models.Order;
import com.aleprimo.nova_store.models.OrderItem;
import com.aleprimo.nova_store.models.Product;
import com.aleprimo.nova_store.models.Shipping;
import com.aleprimo.nova_store.models.enums.OrderStatus;
import com.aleprimo.nova_store.models.enums.PaymentMethod;
import com.aleprimo.nova_store.models.enums.ShippingStatus;
import com.aleprimo.nova_store.persistence.OrderDAO;
import com.aleprimo.nova_store.persistence.ShippingDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PurchaseHistoryServiceImplTest {

    @Mock
    private OrderDAO orderDAO;

    @Mock
    private ShippingDAO shippingDAO;

    @Mock
    private PurchaseHistoryMapper purchaseHistoryMapper;

    @InjectMocks
    private PurchaseHistoryServiceImpl purchaseHistoryService;

    private Customer customer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        customer = Customer.builder().id(1L).build();
    }

    @Test
    void testGetPurchaseHistoryByCustomerId() {
        Order order = Order.builder()
                .id(1L)
                .orderDate(LocalDateTime.now())
                .totalAmount(BigDecimal.valueOf(100))
                .paymentMethod(PaymentMethod.CREDIT_CARD)
                .status(OrderStatus.PROCESSING)
                .customer(customer)
                .orderItems(Collections.singletonList(OrderItem.builder()
                        .product(Product.builder().name("Producto 1").build())
                        .quantity(2)
                        .price(BigDecimal.valueOf(50))
                        .build()))
                .build();

        Shipping shipping = Shipping.builder()
                .order(order)
                .address("Av. Siempre Viva 123")
                .city("Springfield")
                .postalCode("1234")
                .country("USA")
                .status(ShippingStatus.SHIPPED)
                .shippedAt(LocalDateTime.now())
                .deliveredAt(LocalDateTime.now().plusDays(2))
                .build();

        when(orderDAO.findByCustomerId(1L)).thenReturn(List.of(order));
        when(shippingDAO.findByOrderId(1L)).thenReturn(shipping);

        PurchaseHistoryDTO dto = PurchaseHistoryDTO.builder()
                .orderId(order.getId())
                .orderDate(order.getOrderDate())
                .totalAmount(order.getTotalAmount())
                .paymentMethod(order.getPaymentMethod())
                .orderStatus(order.getStatus())
                .items(List.of(OrderItemDTO.builder()
                        .productName("Producto 1")
                        .quantity(2)
                        .price(BigDecimal.valueOf(50))
                        .build()))
                .shippingAddress(shipping.getAddress())
                .shippingCity(shipping.getCity())
                .shippingPostalCode(shipping.getPostalCode())
                .shippingCountry(shipping.getCountry())
                .shippingStatus(shipping.getStatus())
                .shippedAt(shipping.getShippedAt())
                .deliveredAt(shipping.getDeliveredAt())
                .build();

        when(purchaseHistoryMapper.toDTO(order, shipping)).thenReturn(dto);

        List<PurchaseHistoryDTO> result = purchaseHistoryService.getPurchaseHistoryByCustomerId(1L);

        assertEquals(1, result.size());
        assertEquals(order.getId(), result.get(0).getOrderId());
        assertEquals("Producto 1", result.get(0).getItems().get(0).getProductName());

        verify(orderDAO, times(1)).findByCustomerId(1L);
        verify(shippingDAO, times(1)).findByOrderId(1L);
        verify(purchaseHistoryMapper, times(1)).toDTO(order, shipping);
    }
}
