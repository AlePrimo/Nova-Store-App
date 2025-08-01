package com.aleprimo.nova_store.controller;

import com.aleprimo.nova_store.dto.shoppingCart.ShoppingCartRequestDTO;
import com.aleprimo.nova_store.dto.shoppingCart.ShoppingCartResponseDTO;
import com.aleprimo.nova_store.entityServices.ShoppingCartService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;


import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WithMockUser(roles = "ADMIN")
@WebMvcTest(ShoppingCartController.class)
@ActiveProfiles("test")
class ShoppingCartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShoppingCartService shoppingCartService;

    @Autowired
    private ObjectMapper objectMapper;

    private ShoppingCartResponseDTO responseDTO;
    private ShoppingCartRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        responseDTO = ShoppingCartResponseDTO.builder()
                .id(1L)
                .customerId(1L)
                .createdAt(LocalDateTime.now())
                .build();

        requestDTO = ShoppingCartRequestDTO.builder()
                .customerId(1L)
                .build();
    }

    @Test
    void testGetById() throws Exception {
        Mockito.when(shoppingCartService.getCartById(1L)).thenReturn(responseDTO);

        mockMvc.perform(get("/api/shopping-carts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testGetAll() throws Exception {
        Page<ShoppingCartResponseDTO> page = new PageImpl<>(List.of(responseDTO));
        Mockito.when(shoppingCartService.getAllCarts(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/api/shopping-carts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1L));
    }

    @Test
    void testCreate() throws Exception {
        Mockito.when(shoppingCartService.createCart(any())).thenReturn(responseDTO);

        mockMvc.perform(post("/api/shopping-carts").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testUpdate() throws Exception {
        Mockito.when(shoppingCartService.update(eq(1L), any())).thenReturn(responseDTO);

        mockMvc.perform(put("/api/shopping-carts/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete("/api/shopping-carts/1").with(csrf()))
                .andExpect(status().isNoContent());
    }
}
