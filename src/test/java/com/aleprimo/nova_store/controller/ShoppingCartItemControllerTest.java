package com.aleprimo.nova_store.controller;


import com.aleprimo.nova_store.dto.shoppingCartItem.ShoppingCartItemRequestDTO;
import com.aleprimo.nova_store.dto.shoppingCartItem.ShoppingCartItemResponseDTO;
import com.aleprimo.nova_store.entityServices.ShoppingCartItemService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.mockito.ArgumentMatchers.any;

@WebMvcTest(ShoppingCartItemController.class)
@ActiveProfiles("test")
@WithMockUser(roles = "ADMIN")
class ShoppingCartItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShoppingCartItemService shoppingCartItemService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testAddItemToCart() throws Exception {
        ShoppingCartItemRequestDTO requestDTO = ShoppingCartItemRequestDTO.builder()
                .shoppingCartId(1L)
                .productId(2L)
                .quantity(3)
                .build();

        ShoppingCartItemResponseDTO responseDTO = ShoppingCartItemResponseDTO.builder()
                .id(1L)
                .productName("Sample Product")
                .quantity(3)
                .price(BigDecimal.valueOf(300))
                .build();

        Mockito.when(shoppingCartItemService.addItemToCart(any())).thenReturn(responseDTO);

        mockMvc.perform(post("/api/v1/shopping-cart-items")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.productName").value("Sample Product"));
    }

    @Test
    void testDeleteItemFromCart() throws Exception {
        Long itemId = 5L;

        mockMvc.perform(delete("/api/v1/shopping-cart-items/{id}", itemId)
                        .with(csrf()))

                .andExpect(status().isNoContent());
    }
}
