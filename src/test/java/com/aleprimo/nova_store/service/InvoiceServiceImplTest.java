package com.aleprimo.nova_store.service;


import com.aleprimo.nova_store.controller.mappers.InvoiceMapper;
import com.aleprimo.nova_store.dto.invoice.InvoiceRequestDTO;
import com.aleprimo.nova_store.dto.invoice.InvoiceResponseDTO;
import com.aleprimo.nova_store.entityServices.implementations.InvoiceServiceImpl;
import com.aleprimo.nova_store.models.Invoice;
import com.aleprimo.nova_store.models.Order;
import com.aleprimo.nova_store.models.enums.OrderStatus;
import com.aleprimo.nova_store.models.enums.PaymentMethod;
import com.aleprimo.nova_store.persistence.InvoiceDAO;
import com.aleprimo.nova_store.persistence.OrderDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class InvoiceServiceImplTest {

    @InjectMocks
    private InvoiceServiceImpl invoiceService;

    @Mock
    private InvoiceDAO invoiceDAO;
    @Mock
    private OrderDAO orderDAO;
    @Mock
    private InvoiceMapper invoiceMapper;

    @Captor
    private ArgumentCaptor<Invoice> invoiceCaptor;

    private Invoice invoice;
    private InvoiceRequestDTO requestDTO;
    private InvoiceResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        Order order = new Order();
        order.setId(1L);
        order.setPaymentMethod(PaymentMethod.CASH);
        order.setTotalAmount(BigDecimal.valueOf(100));
        order.setOrderStatus(OrderStatus.PENDING);
        order.setCreatedAt(LocalDateTime.now());


        invoice = new Invoice();
        invoice.setId(1L);
        invoice.setOrder(order);

        invoice.setIssuedAt(LocalDateTime.now());
        invoice.setTotalAmount(BigDecimal.valueOf(150));

        requestDTO = InvoiceRequestDTO.builder()
                .orderId(1L)
                .build();

        responseDTO = InvoiceResponseDTO.builder()
                .id(1L)
                .orderId(1L)
                .totalAmount(invoice.getTotalAmount())
                .build();
    }

    @Test
    void testSaveInvoice() {
        when(invoiceMapper.toEntity(requestDTO)).thenReturn(invoice);
        when(invoiceDAO.save(invoice)).thenReturn(invoice);
        when(invoiceMapper.toDto(invoice)).thenReturn(responseDTO);
        when(orderDAO.findById(1L)).thenReturn(Optional.of(invoice.getOrder()));

        InvoiceResponseDTO result = invoiceService.createInvoice(requestDTO);


        verify(invoiceDAO).save(invoiceCaptor.capture());
    }

    @Test
    void testFindById() {
        when(invoiceDAO.findById(1L)).thenReturn(Optional.of(invoice));
        when(invoiceMapper.toDto(invoice)).thenReturn(responseDTO);

        InvoiceResponseDTO result = invoiceService.getInvoiceById(1L);

        assertThat(result.getId()).isEqualTo(1L);
        verify(invoiceDAO).findById(1L);
    }

    @Test
    void testUpdateInvoice() {
        when(invoiceDAO.findById(1L)).thenReturn(Optional.of(invoice));
        when(invoiceMapper.toEntity(requestDTO)).thenReturn(invoice);
        when(invoiceDAO.save(invoice)).thenReturn(invoice);
        when(invoiceMapper.toDto(invoice)).thenReturn(responseDTO);

        InvoiceResponseDTO updated = invoiceService.update(1L, requestDTO);


        verify(invoiceDAO).save(invoiceCaptor.capture());
    }

    @Test
    void testDeleteInvoice() {
        doNothing().when(invoiceDAO).deleteById(1L);
        when(invoiceDAO.existById(1L)).thenReturn(true);

        invoiceService.deleteInvoice(1L);

        verify(invoiceDAO).deleteById(1L);
    }

    @Test
    void testFindAllInvoices() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Invoice> page = new PageImpl<>(java.util.List.of(invoice));

        when(invoiceDAO.findAll(pageable)).thenReturn(page);
        when(invoiceMapper.toDto(invoice)).thenReturn(responseDTO);

        Page<InvoiceResponseDTO> result = invoiceService.getAllInvoices(pageable);

        assertThat(result.getContent()).hasSize(1);

    }
}
