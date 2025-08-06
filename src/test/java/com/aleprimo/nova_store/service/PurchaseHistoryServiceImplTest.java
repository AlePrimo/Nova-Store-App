package com.aleprimo.nova_store.service;

import com.aleprimo.nova_store.controller.mappers.PurchaseHistoryMapper;
import com.aleprimo.nova_store.dto.purchaseHistory.OrderItemDTO;
import com.aleprimo.nova_store.dto.purchaseHistory.PurchaseHistoryDTO;
import com.aleprimo.nova_store.entityServices.implementations.PurchaseHistoryServiceImpl;
import com.aleprimo.nova_store.models.*;
import com.aleprimo.nova_store.models.enums.OrderStatus;
import com.aleprimo.nova_store.models.enums.PaymentMethod;
import com.aleprimo.nova_store.models.enums.ShippingStatus;
import com.aleprimo.nova_store.persistence.OrderDAO;
import com.aleprimo.nova_store.persistence.ShippingDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
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
    private Order order;
    private Shipping shipping;
    private PurchaseHistoryDTO dto;
    private Pageable pageable;

    @BeforeEach
    void setUp() {
        customer = Customer.builder().id(1L).build();
        pageable = PageRequest.of(0, 10);

        order = Order.builder()
                .id(1L)
                .createdAt(LocalDateTime.now())
                .totalAmount(BigDecimal.valueOf(100))
                .paymentMethod(PaymentMethod.CREDIT_CARD)
                .orderStatus(OrderStatus.PROCESSING)
                .customer(customer)
                .orderItems(Collections.singletonList(
                        OrderItem.builder()
                                .product(Product.builder().name("Producto 1").build())
                                .quantity(2)
                                .unitPrice(BigDecimal.valueOf(50))
                                .build()))
                .build();

        shipping = Shipping.builder()
                .order(order)
                .address("Av. Siempre Viva 123")
                .city("Springfield")
                .postalCode("1234")
                .country("USA")
                .status(ShippingStatus.SHIPPED)
                .shippedAt(LocalDateTime.now())
                .deliveredAt(LocalDateTime.now().plusDays(2))
                .build();

        dto = PurchaseHistoryDTO.builder()
                .orderId(order.getId())
                .orderDate(order.getCreatedAt())
                .totalAmount(order.getTotalAmount())
                .paymentMethod(order.getPaymentMethod())
                .orderStatus(order.getOrderStatus())
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
    }

    @Test
    void testGetPurchaseHistoryByCustomerId() {
        when(orderDAO.findByCustomerId(1L, pageable)).thenReturn(new PageImpl<>(List.of(order)));
        when(shippingDAO.findByOrderId(1L)).thenReturn(Optional.of(shipping));
        when(purchaseHistoryMapper.toDTO(order, shipping)).thenReturn(dto); // <- CORREGIDO

        Page<PurchaseHistoryDTO> result = purchaseHistoryService.getPurchaseHistoryByCustomerId(1L, pageable);

        assertEquals(1, result.getContent().size());
        assertEquals(order.getId(), result.getContent().get(0).getOrderId());
        assertEquals("Producto 1", result.getContent().get(0).getItems().get(0).getProductName());

        verify(orderDAO, times(1)).findByCustomerId(1L, pageable);
        verify(shippingDAO, times(1)).findByOrderId(1L);
        verify(purchaseHistoryMapper, times(1)).toDTO(order, shipping);
    }
}

