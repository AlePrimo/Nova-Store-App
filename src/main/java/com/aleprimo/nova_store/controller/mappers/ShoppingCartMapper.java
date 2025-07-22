package com.aleprimo.nova_store.controller.mappers;


import com.aleprimo.nova_store.dto.shoppingCart.ShoppingCartRequestDTO;
import com.aleprimo.nova_store.dto.shoppingCart.ShoppingCartResponseDTO;
import com.aleprimo.nova_store.models.ShoppingCart;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.stream.Collectors;

@Component
public class ShoppingCartMapper {

    private final ShoppingCartItemMapper itemMapper;

    public ShoppingCartMapper(ShoppingCartItemMapper itemMapper) {
        this.itemMapper = itemMapper;
    }

    public ShoppingCartResponseDTO toDto(ShoppingCart entity) {
        if (entity == null) return null;

        return ShoppingCartResponseDTO.builder()
                .id(entity.getId())
                .customerId(entity.getCustomer().getId())
                .createdAt(entity.getCreatedAt())
                .items(entity.getItems() != null
                        ? entity.getItems().stream().map(itemMapper::toDto).collect(Collectors.toList())
                        : Collections.emptyList())
                .build();
    }

    public ShoppingCart toEntity(ShoppingCartRequestDTO dto) {
        if (dto == null) return null;

        ShoppingCart cart = new ShoppingCart();

        return cart;
    }

    public void updateEntityFromDto(ShoppingCartRequestDTO dto, ShoppingCart cart) {

    }
}
