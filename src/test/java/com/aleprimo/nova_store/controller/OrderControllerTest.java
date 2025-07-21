package com.aleprimo.nova_store.controller;

import com.aleprimo.nova_store.dto.order.OrderRequestDTO;
import com.aleprimo.nova_store.dto.order.OrderResponseDTO;
import com.aleprimo.nova_store.entityServices.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
@ActiveProfiles("test")
@WithMockUser(username = "admin", roles = {"ADMIN"})
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;

    private OrderResponseDTO orderResponseDTO;
    private OrderRequestDTO orderRequestDTO;

    @BeforeEach
    void setUp() {
        orderRequestDTO = OrderRequestDTO.builder()
                .customerId(1L)
                .total(BigDecimal.valueOf(120.50))
                .status("CREATED")
                .build();

        orderResponseDTO = OrderResponseDTO.builder()
                .id(1L)
                .customerId(1L)
                .total(BigDecimal.valueOf(120.50))
                .status("CREATED")
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Test
    void getAll_ShouldReturnPageOfOrders() throws Exception {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id"));
        Page<OrderResponseDTO> page = new PageImpl<>(List.of(orderResponseDTO));

        when(orderService.getAll(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/api/orders").param("page", "0").param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(orderResponseDTO.getId()));
    }

    @Test
    void getById_ShouldReturnOrder() throws Exception {
        when(orderService.getById(1L)).thenReturn(orderResponseDTO);

        mockMvc.perform(get("/api/orders/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(orderResponseDTO.getId()));
    }

    @Test
    void create_ShouldReturnCreatedOrder() throws Exception {
        when(orderService.create(any(OrderRequestDTO.class))).thenReturn(orderResponseDTO);

        mockMvc.perform(post("/api/orders")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(orderResponseDTO.getId()));
    }

    @Test
    void update_ShouldReturnUpdatedOrder() throws Exception {
        when(orderService.update(eq(1L), any(OrderRequestDTO.class))).thenReturn(orderResponseDTO);

        mockMvc.perform(put("/api/orders/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(orderResponseDTO.getId()));
    }

    @Test
    void delete_ShouldReturnNoContent() throws Exception {
        doNothing().when(orderService).deleteById(1L);

        mockMvc.perform(delete("/api/orders/1")
                        .with(csrf()))
                .andExpect(status().isNoContent());
    }
}
