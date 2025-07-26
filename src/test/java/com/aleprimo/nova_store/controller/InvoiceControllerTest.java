package com.aleprimo.nova_store.controller;

import com.aleprimo.nova_store.dto.invoice.InvoiceRequestDTO;
import com.aleprimo.nova_store.dto.invoice.InvoiceResponseDTO;

import com.aleprimo.nova_store.entityServices.InvoiceService;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(InvoiceController.class)
@ActiveProfiles("test")
@WithMockUser(roles = "ADMIN")
class InvoiceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InvoiceService invoiceService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateInvoice() throws Exception {
        InvoiceRequestDTO requestDTO = InvoiceRequestDTO.builder()
                .orderId(1L)
                .build();

        InvoiceResponseDTO responseDTO = InvoiceResponseDTO.builder()
                .id(1L)

                .orderId(1L)


                .totalAmount(BigDecimal.valueOf(100))
                .build();

        Mockito.when(invoiceService.createInvoice(any())).thenReturn(responseDTO);

        mockMvc.perform(post("/api/invoices")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.totalAmount").value(100));

    }

    @Test
    void testGetInvoiceById() throws Exception {
        InvoiceResponseDTO dto = InvoiceResponseDTO.builder()
                .id(1L)

                .orderId(2L)


                .totalAmount(BigDecimal.valueOf(100))
                .build();

        Mockito.when(invoiceService.getInvoiceById(1L)).thenReturn(dto);

        mockMvc.perform(get("/api/invoices/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalAmount").value(100));

    }

    @Test
    void testFindAllInvoices() throws Exception {
        InvoiceResponseDTO dto = InvoiceResponseDTO.builder()
                .id(1L)
                .orderId(3L)
                .totalAmount(BigDecimal.valueOf(100))
                .build();

        Page<InvoiceResponseDTO> page = new PageImpl<>(List.of(dto));
        Mockito.when(invoiceService.getAllInvoices(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/api/invoices"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].totalAmount").value(100));


    }

    @Test
    void testUpdateInvoice() throws Exception {
        InvoiceRequestDTO requestDTO = InvoiceRequestDTO.builder()

                .orderId(4L)



                .build();

        InvoiceResponseDTO responseDTO = InvoiceResponseDTO.builder()
                .id(4L)

                .orderId(4L)

                .totalAmount(BigDecimal.valueOf(100))
                .build();

        Mockito.when(invoiceService.update(Mockito.eq(4L), any())).thenReturn(responseDTO);

        mockMvc.perform(put("/api/invoices/4")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalAmount").value(100));

    }

    @Test
    void testDeleteInvoice() throws Exception {
        doNothing().when(invoiceService).deleteInvoice(5L);

        mockMvc.perform(delete("/api/invoices/5").with(csrf()))
                .andExpect(status().isNoContent());
    }
}
