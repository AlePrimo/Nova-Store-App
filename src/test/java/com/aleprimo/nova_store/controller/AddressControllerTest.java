package com.aleprimo.nova_store.controller;


import com.aleprimo.nova_store.controller.mappers.AddressMapper;
import com.aleprimo.nova_store.dto.adress.AddressRequestDTO;
import com.aleprimo.nova_store.dto.adress.AddressResponseDTO;
import com.aleprimo.nova_store.entityServices.AddressService;
import com.aleprimo.nova_store.entityServices.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AddressController.class)
@ActiveProfiles("test")
@WithMockUser(username = "admin", roles = {"ADMIN"})
class AddressControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AddressService addressService;
    @MockBean
    private CustomerService customerService;
    @MockBean
    private AddressMapper addressMapper;
    @Autowired
    private ObjectMapper objectMapper;

    private AddressRequestDTO requestDTO;
    private AddressResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        requestDTO = AddressRequestDTO.builder()
                .street("Fake St")
                .city("Faketown")
                .province("FS")
                .postalCode("12345")
                .country("FC")
                .customerId(1L)
                .build();

        responseDTO = AddressResponseDTO.builder()
                .id(1L)
                .street("Fake St")
                .city("Faketown")
                .province("FS")
                .postalCode("12345")
                .country("FC")
                .build();
    }

    @Test
    void testCreateAddress() throws Exception {
        Mockito.when(addressService.createAddress(any())).thenReturn(responseDTO);

        mockMvc.perform(post("/api/v1/addresses")
                        .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testGetAllAddresses() throws Exception {
        Page<AddressResponseDTO> page = new PageImpl<>(List.of(responseDTO));
        Mockito.when(addressService.getAllAddresses(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/api/v1/addresses"))
                .andExpect(status().isOk())
               .andExpect(jsonPath("$[0].id").value(1L));
    }

    @Test
    void testGetAddressById() throws Exception {
        Mockito.when(addressService.getAddressById(1L)).thenReturn(responseDTO);

        mockMvc.perform(get("/api/v1/addresses/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testDeleteAddress() throws Exception {
        Mockito.doNothing().when(addressService).deleteAddress(1L);

        mockMvc.perform(delete("/api/v1/addresses/1").with(csrf()))
                .andExpect(status().isNoContent());
    }
}
