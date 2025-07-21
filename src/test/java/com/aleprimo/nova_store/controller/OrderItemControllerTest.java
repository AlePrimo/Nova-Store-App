package com.aleprimo.nova_store.controller;

import com.aleprimo.nova_store.dto.orderItem.OrderItemRequestDTO;
import com.aleprimo.nova_store.dto.orderItem.OrderItemResponseDTO;
import com.aleprimo.nova_store.entityServices.OrderItemService;
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
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderItemController.class)
@ActiveProfiles("test")
@WithMockUser(username = "admin", roles = {"ADMIN"})
class OrderItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderItemService orderItemService;

    @Autowired
    private ObjectMapper objectMapper;

    private OrderItemRequestDTO requestDTO;
    private OrderItemResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        requestDTO = OrderItemRequestDTO.builder()
                .orderId(1L)
                .productId(2L)
                .quantity(3)
                .unitPrice(BigDecimal.valueOf(99.99))
                .build();

        responseDTO = OrderItemResponseDTO.builder()
                .id(10L)
                .orderId(1L)
                .productId(2L)
                .quantity(3)
                .unitPrice(BigDecimal.valueOf(99.99))
                .build();
    }

    @Test
    void getAll_ShouldReturnPagedOrderItems() throws Exception {
        Page<OrderItemResponseDTO> page = new PageImpl<>(List.of(responseDTO));
        when(orderItemService.getAll(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/api/order-items?page=0&size=10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(responseDTO.getId()));
    }

    @Test
    void getById_ShouldReturnOrderItem() throws Exception {
        when(orderItemService.getById(10L)).thenReturn(responseDTO);

        mockMvc.perform(get("/api/order-items/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(2));
    }

    @Test
    void create_ShouldReturnCreatedOrderItem() throws Exception {
        when(orderItemService.create(any(OrderItemRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/api/order-items")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(responseDTO.getId()))
                .andExpect(jsonPath("$.quantity").value(responseDTO.getQuantity()));
    }

    @Test
    void update_ShouldReturnUpdatedOrderItem() throws Exception {
        when(orderItemService.update(eq(10L), any(OrderItemRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(put("/api/order-items/10")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(responseDTO.getId()))
                .andExpect(jsonPath("$.unitPrice").value(responseDTO.getUnitPrice()));
    }

    @Test
    void delete_ShouldReturnNoContent() throws Exception {
        doNothing().when(orderItemService).deleteById(10L);

        mockMvc.perform(delete("/api/order-items/10").with(csrf()))
                .andExpect(status().isNoContent());
    }
}
