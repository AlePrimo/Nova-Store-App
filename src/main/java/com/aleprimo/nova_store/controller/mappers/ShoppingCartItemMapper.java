package com.aleprimo.nova_store.controller.mappers;


import com.aleprimo.nova_store.dto.shoppingCartItem.ShoppingCartItemRequestDTO;
import com.aleprimo.nova_store.dto.shoppingCartItem.ShoppingCartItemResponseDTO;
import com.aleprimo.nova_store.models.ShoppingCartItem;
import org.springframework.stereotype.Component;

@Component
public class ShoppingCartItemMapper {

    public ShoppingCartItemResponseDTO toDto(ShoppingCartItem item) {
        if (item == null) return null;

        return ShoppingCartItemResponseDTO.builder()
                .id(item.getId())
                .productId(item.getProduct().getId())
                .productName(item.getProduct().getName())
                .quantity(item.getQuantity())
                .price(item.getProduct().getPrice())
                .build();
    }

    public ShoppingCartItem toEntity(ShoppingCartItemRequestDTO dto) {
        if (dto == null) return null;

        return ShoppingCartItem.builder()
                .quantity(dto.getQuantity())
                .build();

    }

    public void updateEntityFromDto(ShoppingCartItemRequestDTO dto, ShoppingCartItem item) {
        if (dto == null || item == null) return;

        item.setQuantity(dto.getQuantity());

    }
}
