package com.aleprimo.nova_store.controller.mappers;

import com.aleprimo.nova_store.dto.invoice.InvoiceRequestDTO;
import com.aleprimo.nova_store.dto.invoice.InvoiceResponseDTO;
import com.aleprimo.nova_store.models.Invoice;
import com.aleprimo.nova_store.models.Order;
import org.springframework.stereotype.Component;

@Component
public class InvoiceMapper {


    public InvoiceResponseDTO toDto(Invoice invoice) {
        if (invoice == null) return null;

        return InvoiceResponseDTO.builder()
                .id(invoice.getId())
                .orderId(invoice.getOrder().getId())
                .totalAmount(invoice.getTotalAmount())
                .createdAt(invoice.getIssuedAt())
                .build();
    }

    public Invoice toEntity(InvoiceRequestDTO dto) {
        if (dto == null) return null;

        return Invoice.builder()
                .order(Order.builder().id(dto.getOrderId()).build())
                .build();
    }

    public void updateEntityFromDto(InvoiceRequestDTO dto, Invoice invoice) {
        if (dto == null || invoice == null) return;

        invoice.setOrder(Order.builder().id(dto.getOrderId()).build());
    }
}
