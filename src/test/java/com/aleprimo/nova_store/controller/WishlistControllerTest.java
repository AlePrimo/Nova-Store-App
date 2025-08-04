package com.aleprimo.nova_store.controller;


import com.aleprimo.nova_store.dto.whishlist.WishlistRequestDTO;
import com.aleprimo.nova_store.dto.whishlist.WishlistResponseDTO;
import com.aleprimo.nova_store.entityServices.WishlistService;
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

import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(WishlistController.class)
@ActiveProfiles("test")
@WithMockUser(username = "admin", roles = {"ADMIN"})
class WishlistControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WishlistService wishlistService;

    @Autowired
    private ObjectMapper objectMapper;

    private WishlistResponseDTO responseDTO;
    private WishlistRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        requestDTO = WishlistRequestDTO.builder()
                .name("WISHLIST")
                .customerId(1L)
                .productIds(List.of(1L, 2L))
                .build();

        responseDTO = WishlistResponseDTO.builder()
                .id(1L)
                .name("WISHLIST")
                .customerId(1L)
                .productIds(List.of(1L, 2L))
                .build();
    }

    @Test
    void testCreateWishlist() throws Exception {
        Mockito.when(wishlistService.create(any())).thenReturn(responseDTO);

        mockMvc.perform(post("/api/v1/wishlists")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testGetWishlistById() throws Exception {
        Mockito.when(wishlistService.getById(1L)).thenReturn(responseDTO);

        mockMvc.perform(get("/api/v1/wishlists/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").value(1L));
    }

    @Test
    void testGetAllWishlists() throws Exception {
        Page<WishlistResponseDTO> page = new PageImpl<>(List.of(responseDTO));
        Mockito.when(wishlistService.getAll(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/api/v1/wishlists"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1L));
    }

    @Test
    void testUpdateWishlist() throws Exception {
        Mockito.when(wishlistService.updateWishlist(eq(1L), any())).thenReturn(responseDTO);

        mockMvc.perform(put("/api/v1/wishlists/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productIds").isArray());
    }

    @Test
    void testDeleteWishlist() throws Exception {
        Mockito.doNothing().when(wishlistService).delete(1L);

        mockMvc.perform(delete("/api/v1/wishlists/1").with(csrf()))
                .andExpect(status().isNoContent());
    }
}
