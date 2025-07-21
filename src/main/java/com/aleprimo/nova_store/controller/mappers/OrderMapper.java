package com.aleprimo.nova_store.controller.mappers;

import com.aleprimo.nova_store.dto.order.OrderRequestDTO;
import com.aleprimo.nova_store.dto.order.OrderResponseDTO;

import com.aleprimo.nova_store.models.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    public OrderResponseDTO toDto(Order order) {
        if (order == null) return null;

        return OrderResponseDTO.builder()
                .id(order.getId())
                .customerId(order.getCustomer().getId())
                .total(order.getTotalAmount())
                .orderStatus(order.getOrderStatus())
                .createdAt(order.getCreatedAt())
                .build();
    }

    public Order toEntity(OrderRequestDTO dto) {
        if (dto == null) return null;

        Order order = new Order();
        order.setTotalAmount(dto.getTotal());
        order.setOrderStatus(dto.getOrderStatus());

        return order;
    }

    public void updateEntityFromDto(OrderRequestDTO dto, Order order) {
        if (dto == null || order == null) return;

        order.setTotalAmount(dto.getTotal());
        order.setOrderStatus(dto.getOrderStatus());

    }
}
