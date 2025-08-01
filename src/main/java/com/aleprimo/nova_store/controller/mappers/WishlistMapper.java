package com.aleprimo.nova_store.controller.mappers;


import com.aleprimo.nova_store.dto.whishlist.WishlistRequestDTO;
import com.aleprimo.nova_store.dto.whishlist.WishlistResponseDTO;
import com.aleprimo.nova_store.models.Customer;
import com.aleprimo.nova_store.models.Product;
import com.aleprimo.nova_store.models.Wishlist;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class WishlistMapper {

    public Wishlist toEntity(WishlistRequestDTO dto, Customer customer, List<Product> products) {
        return Wishlist.builder()
                .name(dto.getName())
                .createdAt(LocalDateTime.now())
                .customer(customer)
                .products(products)
                .build();
    }

    public WishlistResponseDTO toDTO(Wishlist wishlist) {
        return WishlistResponseDTO.builder()
                .id(wishlist.getId())
                .name(wishlist.getName())
                .createdAt(wishlist.getCreatedAt())
                .customerId(wishlist.getCustomer().getId())
                .productIds(wishlist.getProducts().stream().map(Product::getId).collect(Collectors.toList()))
                .build();
    }
}
