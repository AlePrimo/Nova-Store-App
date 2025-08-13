package com.aleprimo.nova_store.controller.mappers;

import com.aleprimo.nova_store.dto.purchaseHistory.OrderItemDTO;
import com.aleprimo.nova_store.dto.purchaseHistory.PurchaseHistoryDTO;
import com.aleprimo.nova_store.models.Order;
import com.aleprimo.nova_store.models.OrderItem;
import com.aleprimo.nova_store.models.Shipping;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PurchaseHistoryMapper {

    public  PurchaseHistoryDTO toDTO(Order order, Shipping shipping) {
        return PurchaseHistoryDTO.builder()
                .orderId(order.getId())
                .orderDate(order.getCreatedAt())
                .totalAmount(order.getTotalAmount())
                .paymentMethod(order.getPaymentMethod())
                .orderStatus(order.getOrderStatus())
                .items(mapItems(order.getOrderItems()))
                .shippingAddress(shipping.getAddress())
                .shippingCity(shipping.getCity())
                .shippingPostalCode(shipping.getPostalCode())
                .shippingCountry(shipping.getCountry())
                .shippingStatus(shipping.getStatus())
                .shippedAt(shipping.getShippedAt())
                .deliveredAt(shipping.getDeliveredAt())
                .build();
    }

    private static List<OrderItemDTO> mapItems(List<OrderItem> items) {
        return items.stream()
                .map(item -> OrderItemDTO.builder()
                        .productName(item.getProduct().getName())
                        .quantity(item.getQuantity())
                        .price(item.getUnitPrice())
                        .build())
                .collect(Collectors.toList());
    }
}
