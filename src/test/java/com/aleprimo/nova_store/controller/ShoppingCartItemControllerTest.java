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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import org.springframework.data.domain.PageImpl;
import java.util.List;
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

        mockMvc.perform(post("/api/cart-items/create")
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

        mockMvc.perform(delete("/api/cart-items/{id}", itemId)
                        .with(csrf()))

                .andExpect(status().isNoContent());
    }



    @Test
    void testGetAllCartItems() throws Exception {
        PageImpl<ShoppingCartItemResponseDTO> page = new PageImpl<>(
                List.of(
                        ShoppingCartItemResponseDTO.builder()
                                .id(1L)
                                .productName("Product 1")
                                .quantity(2)
                                .price(BigDecimal.valueOf(100))
                                .build(),
                        ShoppingCartItemResponseDTO.builder()
                                .id(2L)
                                .productName("Product 2")
                                .quantity(1)
                                .price(BigDecimal.valueOf(200))
                                .build()
                )
        );

        Mockito.when(shoppingCartItemService.getAllItems(any())).thenReturn(page);

        mockMvc.perform(get("/api/cart-items")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(2))
                .andExpect(jsonPath("$.content[0].productName").value("Product 1"))
                .andExpect(jsonPath("$.content[1].productName").value("Product 2"));
    }

    @Test
    void testGetCartItemById() throws Exception {
        Long itemId = 1L;
        ShoppingCartItemResponseDTO responseDTO = ShoppingCartItemResponseDTO.builder()
                .id(itemId)
                .productName("Sample Product")
                .quantity(2)
                .price(BigDecimal.valueOf(123.45))
                .build();

        Mockito.when(shoppingCartItemService.getItemById(itemId)).thenReturn(responseDTO);

        mockMvc.perform(get("/api/cart-items/{id}", itemId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(itemId))
                .andExpect(jsonPath("$.productName").value("Sample Product"));
    }

    @Test
    void testUpdateCartItem() throws Exception {
        Long itemId = 1L;

        ShoppingCartItemRequestDTO requestDTO = ShoppingCartItemRequestDTO.builder()
                .shoppingCartId(1L)
                .productId(2L)
                .quantity(4)
                .build();

        ShoppingCartItemResponseDTO responseDTO = ShoppingCartItemResponseDTO.builder()
                .id(itemId)
                .productName("Updated Product")
                .quantity(4)
                .price(BigDecimal.valueOf(400))
                .build();

        Mockito.when(shoppingCartItemService.update(Mockito.eq(itemId), any()))
                .thenReturn(responseDTO);

        mockMvc.perform(put("/api/cart-items/{id}", itemId)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(itemId))
                .andExpect(jsonPath("$.productName").value("Updated Product"))
                .andExpect(jsonPath("$.quantity").value(4));
    }





}
