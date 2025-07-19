package com.aleprimo.nova_store.controller.mappers;


import com.aleprimo.nova_store.dto.orderItem.OrderItemRequestDTO;
import com.aleprimo.nova_store.dto.orderItem.OrderItemResponseDTO;
import com.aleprimo.nova_store.models.OrderItem;
import org.springframework.stereotype.Component;

@Component
public class OrderItemMapper {

    public OrderItemResponseDTO toDto(OrderItem orderItem) {
        if (orderItem == null) return null;

        return OrderItemResponseDTO.builder()
                .id(orderItem.getId())
                .orderId(orderItem.getOrder().getId())
                .productId(orderItem.getProduct().getId())
                .quantity(orderItem.getQuantity())
                .unitPrice(orderItem.getUnitPrice())
                .build();
    }

    public OrderItem toEntity(OrderItemRequestDTO dto) {
        if (dto == null) return null;

        OrderItem orderItem = new OrderItem();
        orderItem.setQuantity(dto.getQuantity());
        orderItem.setUnitPrice(dto.getUnitPrice());


        return orderItem;
    }

    public void updateEntityFromDto(OrderItemRequestDTO dto, OrderItem orderItem) {
        if (dto == null || orderItem == null) return;

        orderItem.setQuantity(dto.getQuantity());
        orderItem.setUnitPrice(dto.getUnitPrice());

    }
}
