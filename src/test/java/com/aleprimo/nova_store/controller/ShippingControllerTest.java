package com.aleprimo.nova_store.controller;


import com.aleprimo.nova_store.dto.shipping.ShippingRequestDTO;
import com.aleprimo.nova_store.dto.shipping.ShippingResponseDTO;
import com.aleprimo.nova_store.entityServices.ShippingService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ShippingController.class)
@ActiveProfiles("test")
@WithMockUser(username = "admin", roles = {"ADMIN"})
class ShippingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShippingService shippingService;

    @Autowired
    private ObjectMapper objectMapper;

    private ShippingRequestDTO requestDTO;
    private ShippingResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        requestDTO = ShippingRequestDTO.builder()
                .address("123 Calle")
                .orderId(1L)
                .city("Ciudad")
                .country("País")
                .postalCode("1234")
                .build();

        responseDTO = ShippingResponseDTO.builder()
                .id(1L)
                .orderId(1L)
                .address("123 Calle")
                .city("Ciudad")
                .country("País")
                .postalCode("1234")
                .build();
    }

    @Test
    void testCreateShipping() throws Exception {
        when(shippingService.createShipping(any())).thenReturn(responseDTO);

        mockMvc.perform(post("/api/shippings")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.address").value("123 Calle"));
    }

    @Test
    void testGetShippingById() throws Exception {
        when(shippingService.getShippingById(1L)).thenReturn(responseDTO);

        mockMvc.perform(get("/api/shippings/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.city").value("Ciudad"));
    }

    @Test
    void testUpdateShipping() throws Exception {
        when(shippingService.updateShipping(eq(1L), any())).thenReturn(responseDTO);

        mockMvc.perform(put("/api/shippings/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.postalCode").value("1234"));
    }

    @Test
    void testDeleteShipping() throws Exception {
        doNothing().when(shippingService).deleteShipping(1L);

        mockMvc.perform(delete("/api/shippings/1").with(csrf()))

                .andExpect(status().isNoContent());
    }

    @Test
    void testGetAllShippings() throws Exception {
        Page<ShippingResponseDTO> page = new PageImpl<>(List.of(responseDTO));
        when(shippingService.getAllShippings(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/api/shippings"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].address").value("123 Calle"));
    }
}
