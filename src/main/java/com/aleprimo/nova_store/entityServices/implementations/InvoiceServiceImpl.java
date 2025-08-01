package com.aleprimo.nova_store.entityServices.implementations;

import com.aleprimo.nova_store.controller.mappers.InvoiceMapper;

import com.aleprimo.nova_store.dto.invoice.InvoiceRequestDTO;
import com.aleprimo.nova_store.dto.invoice.InvoiceResponseDTO;
import com.aleprimo.nova_store.entityServices.InvoiceService;
import com.aleprimo.nova_store.handler.exceptions.ResourceNotFoundException;
import com.aleprimo.nova_store.models.Invoice;
import com.aleprimo.nova_store.models.Order;
import com.aleprimo.nova_store.persistence.InvoiceDAO;
import com.aleprimo.nova_store.persistence.OrderDAO;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceDAO invoiceDAO;
    private final OrderDAO orderDAO;
    private final InvoiceMapper mapper;

    @Override
    public InvoiceResponseDTO createInvoice(InvoiceRequestDTO dto) {
        Order order = orderDAO.findById(dto.getOrderId())
                .orElseThrow(() -> new EntityNotFoundException("Orden no encontrada"));

        BigDecimal total = order.getOrderItems().stream()
                .map(item -> item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Invoice invoice = Invoice.builder()
                .order(order)
                .totalAmount(total)
                .build();

        return mapper.toDto(invoiceDAO.save(invoice));
    }

    @Override
    public InvoiceResponseDTO update(Long id, InvoiceRequestDTO dto) {
        Invoice invoice = invoiceDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice not found with id: " + id));

        mapper.updateEntityFromDto(dto, invoice);
        Invoice updated = invoiceDAO.save(invoice);

        return mapper.toDto(updated);
    }

    @Override
    public InvoiceResponseDTO getInvoiceById(Long id) {
        return invoiceDAO.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Factura no encontrada"));
    }

    @Override
    public Page<InvoiceResponseDTO> getAllInvoices(Pageable pageable) {
        return invoiceDAO.findAll(pageable).map(mapper::toDto);
    }

    @Override
    public void deleteInvoice(Long id) {
        if (!invoiceDAO.existById(id))
            throw new EntityNotFoundException("Factura no encontrada");
        invoiceDAO.deleteById(id);
    }
}
