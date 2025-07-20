package com.aleprimo.nova_store.service;

import com.aleprimo.nova_store.controller.mappers.OrderMapper;
import com.aleprimo.nova_store.dto.order.OrderRequestDTO;
import com.aleprimo.nova_store.dto.order.OrderResponseDTO;
import com.aleprimo.nova_store.entityServices.implementations.OrderServiceImpl;
import com.aleprimo.nova_store.models.Customer;
import com.aleprimo.nova_store.models.Order;
import com.aleprimo.nova_store.persistence.CustomerDAO;
import com.aleprimo.nova_store.persistence.OrderDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private OrderDAO orderDAO;

    @Mock
    private CustomerDAO customerDAO;

    @Mock
    private OrderMapper orderMapper;

    @InjectMocks
    private OrderServiceImpl orderService;

    private Order order;
    private Customer customer;
    private OrderRequestDTO requestDTO;
    private OrderResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        customer = new Customer();
        customer.setId(1L);

        order = new Order();
        order.setId(10L);
        order.setCustomer(customer);
        order.setTotalAmount(BigDecimal.valueOf(100));
        order.setStatus("PENDING");
        order.setCreatedAt(LocalDateTime.now());

        requestDTO = OrderRequestDTO.builder()
                .customerId(1L)
                .total(BigDecimal.valueOf(100))
                .status("PENDING")
                .build();

        responseDTO = OrderResponseDTO.builder()
                .id(10L)
                .customerId(1L)
                .total(BigDecimal.valueOf(100))
                .status("PENDING")
                .createdAt(order.getCreatedAt())
                .build();
    }

    @Test
    void testGetAll() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Order> page = new PageImpl<>(List.of(order));

        when(orderDAO.findAll(pageable)).thenReturn(page);
        when(orderMapper.toDto(order)).thenReturn(responseDTO);

        Page<OrderResponseDTO> result = orderService.getAll(pageable);

        assertThat(result).isNotNull();
        assertThat(result.getContent()).containsExactly(responseDTO);
        verify(orderDAO).findAll(pageable);
        verify(orderMapper).toDto(order);
    }

    @Test
    void testGetById_found() {
        when(orderDAO.findById(10L)).thenReturn(Optional.of(order));
        when(orderMapper.toDto(order)).thenReturn(responseDTO);

        OrderResponseDTO result = orderService.getById(10L);

        assertThat(result).isEqualTo(responseDTO);
        verify(orderDAO).findById(10L);
    }

    @Test
    void testGetById_notFound() {
        when(orderDAO.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> orderService.getById(99L))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("Orden no encontrada");

        verify(orderDAO).findById(99L);
    }

    @Test
    void testCreate() {
        when(orderMapper.toEntity(requestDTO)).thenReturn(order);
        when(customerDAO.findById(1L)).thenReturn(Optional.of(customer));
        when(orderDAO.save(order)).thenReturn(order);
        when(orderMapper.toDto(order)).thenReturn(responseDTO);

        OrderResponseDTO result = orderService.create(requestDTO);

        assertThat(result).isEqualTo(responseDTO);
        verify(orderMapper).toEntity(requestDTO);
        verify(customerDAO).findById(1L);
        verify(orderDAO).save(order);
        verify(orderMapper).toDto(order);
    }

    @Test
    void testCreate_customerNotFound() {
        when(orderMapper.toEntity(requestDTO)).thenReturn(order);
        when(customerDAO.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> orderService.create(requestDTO))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("Cliente no encontrado");

        verify(customerDAO).findById(1L);
    }

    @Test
    void testUpdate_found() {
        when(orderDAO.findById(10L)).thenReturn(Optional.of(order));
        doNothing().when(orderMapper).updateEntityFromDto(requestDTO, order);
        when(orderDAO.save(order)).thenReturn(order);
        when(orderMapper.toDto(order)).thenReturn(responseDTO);

        OrderResponseDTO result = orderService.update(10L, requestDTO);

        assertThat(result).isEqualTo(responseDTO);
        verify(orderDAO).findById(10L);
        verify(orderMapper).updateEntityFromDto(requestDTO, order);
        verify(orderDAO).save(order);
    }

    @Test
    void testUpdate_notFound() {
        when(orderDAO.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> orderService.update(99L, requestDTO))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("Orden no encontrada");

        verify(orderDAO).findById(99L);
    }

    @Test
    void testDeleteById_found() {
        when(orderDAO.findById(10L)).thenReturn(Optional.of(order));
        doNothing().when(orderDAO).deleteById(10L);

        orderService.deleteById(10L);

        verify(orderDAO).findById(10L);
        verify(orderDAO).deleteById(10L);
    }

    @Test
    void testDeleteById_notFound() {
        when(orderDAO.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> orderService.deleteById(99L))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("Orden no encontrada");

        verify(orderDAO).findById(99L);
    }
}
