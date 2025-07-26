package com.aleprimo.nova_store.controller.mappers;

import com.aleprimo.nova_store.dto.shipping.ShippingRequestDTO;
import com.aleprimo.nova_store.dto.shipping.ShippingResponseDTO;
import com.aleprimo.nova_store.models.Order;
import com.aleprimo.nova_store.models.Shipping;
import org.springframework.stereotype.Component;

@Component
public class ShippingMapper {

    public ShippingResponseDTO toDto(Shipping shipping) {
        if (shipping == null) return null;

        return ShippingResponseDTO.builder()
                .id(shipping.getId())
                .address(shipping.getAddress())
                .city(shipping.getCity())
                .postalCode(shipping.getPostalCode())
                .country(shipping.getCountry())
                .status(shipping.getStatus())
                .orderId(shipping.getOrder() != null ? shipping.getOrder().getId() : null)
                .shippedAt(String.valueOf(shipping.getShippedAt()))
                .build();
    }

    public Shipping toEntity(ShippingRequestDTO dto) {
        if (dto == null) return null;

        return Shipping.builder()
                .address(dto.getAddress())
                .city(dto.getCity())
                .postalCode(dto.getPostalCode())
                .country(dto.getCountry())
                .status(dto.getStatus())
                .order(Order.builder().id(dto.getOrderId()).build())
                .build();
    }

    public void updateEntityFromDto(ShippingRequestDTO dto, Shipping shipping) {
        if (dto == null || shipping == null) return;

        shipping.setAddress(dto.getAddress());
        shipping.setCity(dto.getCity());
        shipping.setPostalCode(dto.getPostalCode());
        shipping.setCountry(dto.getCountry());
        shipping.setStatus(dto.getStatus());
        shipping.setOrder(Order.builder().id(dto.getOrderId()).build());
    }
}
