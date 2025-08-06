package com.aleprimo.nova_store.controller;

import com.aleprimo.nova_store.dto.purchaseHistory.OrderItemDTO;
import com.aleprimo.nova_store.dto.purchaseHistory.PurchaseHistoryDTO;
import com.aleprimo.nova_store.entityServices.PurchaseHistoryService;
import com.aleprimo.nova_store.models.enums.OrderStatus;
import com.aleprimo.nova_store.models.enums.PaymentMethod;
import com.aleprimo.nova_store.models.enums.ShippingStatus;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PurchaseHistoryController.class)
@ActiveProfiles("test")
@WithMockUser(roles = "ADMIN")
class PurchaseHistoryControllerTest {

    @Resource
    private MockMvc mockMvc;

    @MockBean
    private PurchaseHistoryService purchaseHistoryService;

    @Resource
    private ObjectMapper objectMapper;

    @Test
    void testGetPurchaseHistoryByCustomerId() throws Exception {
        Pageable pageable = PageRequest.of(0, 10);
        PurchaseHistoryDTO dto = PurchaseHistoryDTO.builder()
                .orderId(1L)
                .orderDate(LocalDateTime.now())
                .totalAmount(BigDecimal.valueOf(200))
                .paymentMethod(PaymentMethod.DEBIT_CARD)
                .orderStatus(OrderStatus.SHIPPED)
                .items(List.of(OrderItemDTO.builder()
                        .productName("Producto X")
                        .quantity(1)
                        .price(BigDecimal.valueOf(200))
                        .build()))
                .shippingAddress("Calle Falsa 123")
                .shippingCity("Buenos Aires")
                .shippingPostalCode("1000")
                .shippingCountry("Argentina")
                .shippingStatus(ShippingStatus.DELIVERED)
                .shippedAt(LocalDateTime.now().minusDays(3))
                .deliveredAt(LocalDateTime.now())
                .build();

        when(purchaseHistoryService.getPurchaseHistoryByCustomerId(1L,pageable))
                .thenReturn((Page<PurchaseHistoryDTO>) List.of(dto));

        mockMvc.perform(get("/api/purchase-history/customer/1").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].orderId").value(1))
                .andExpect(jsonPath("$[0].items[0].productName").value("Producto X"));
    }
}
