package com.aleprimo.nova_store.service;

import com.aleprimo.nova_store.controller.mappers.OrderItemMapper;
import com.aleprimo.nova_store.dto.orderItem.OrderItemRequestDTO;
import com.aleprimo.nova_store.dto.orderItem.OrderItemResponseDTO;
import com.aleprimo.nova_store.entityServices.implementations.OrderItemServiceImpl;
import com.aleprimo.nova_store.models.Order;
import com.aleprimo.nova_store.models.OrderItem;
import com.aleprimo.nova_store.models.Product;
import com.aleprimo.nova_store.persistence.OrderDAO;
import com.aleprimo.nova_store.persistence.OrderItemDAO;
import com.aleprimo.nova_store.persistence.ProductDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.*;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderItemServiceImplTest {

    @Mock private OrderItemDAO orderItemDAO;
    @Mock private OrderDAO orderDAO;
    @Mock private ProductDAO productDAO;
    @Mock private OrderItemMapper orderItemMapper;

    @InjectMocks private OrderItemServiceImpl orderItemService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAll() {
        OrderItem orderItem = new OrderItem();
        OrderItemResponseDTO dto = new OrderItemResponseDTO();

        Page<OrderItem> page = new PageImpl<>(List.of(orderItem));
        when(orderItemDAO.findAll(any())).thenReturn(page);
        when(orderItemMapper.toDto(orderItem)).thenReturn(dto);

        Page<OrderItemResponseDTO> result = orderItemService.getAll(PageRequest.of(0, 10));

        assertEquals(1, result.getTotalElements());
        verify(orderItemDAO).findAll(any());
        verify(orderItemMapper).toDto(orderItem);
    }

    @Test
    void testGetById_Found() {
        OrderItem item = new OrderItem();
        OrderItemResponseDTO dto = new OrderItemResponseDTO();

        when(orderItemDAO.findById(1L)).thenReturn(Optional.of(item));
        when(orderItemMapper.toDto(item)).thenReturn(dto);

        OrderItemResponseDTO result = orderItemService.getById(1L);
        assertNotNull(result);
        verify(orderItemDAO).findById(1L);
        verify(orderItemMapper).toDto(item);
    }

    @Test
    void testGetById_NotFound() {
        when(orderItemDAO.findById(99L)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> orderItemService.getById(99L));
    }

    @Test
    void testCreate() {
        OrderItemRequestDTO dto = OrderItemRequestDTO.builder()
                .orderId(1L)
                .productId(2L)
                .quantity(5)
                .unitPrice(BigDecimal.valueOf(50))
                .build();

        Order order = new Order();
        order.setId(1L);

        Product product = new Product();
        product.setId(2L);

        OrderItem entity = new OrderItem();
        entity.setQuantity(5);
        entity.setUnitPrice(BigDecimal.valueOf(50));

        OrderItem savedEntity = new OrderItem();
        savedEntity.setId(10L);

        OrderItemResponseDTO responseDTO = OrderItemResponseDTO.builder().id(10L).build();

        when(orderItemMapper.toEntity(dto)).thenReturn(entity);
        when(orderDAO.findById(1L)).thenReturn(Optional.of(order));
        when(productDAO.findById(2L)).thenReturn(Optional.of(product));
        when(orderItemDAO.save(entity)).thenReturn(savedEntity);
        when(orderItemMapper.toDto(savedEntity)).thenReturn(responseDTO);

        OrderItemResponseDTO result = orderItemService.create(dto);

        assertEquals(10L, result.getId());
        verify(orderItemDAO).save(entity);
    }

    @Test
    void testUpdate() {
        OrderItemRequestDTO dto = OrderItemRequestDTO.builder()
                .quantity(3)
                .unitPrice(BigDecimal.valueOf(80))
                .build();

        OrderItem existing = new OrderItem();
        existing.setId(1L);

        OrderItem updated = new OrderItem();
        updated.setId(1L);
        updated.setQuantity(3);
        updated.setUnitPrice(BigDecimal.valueOf(80));

        OrderItemResponseDTO responseDTO = OrderItemResponseDTO.builder().id(1L).quantity(3).build();

        when(orderItemDAO.findById(1L)).thenReturn(Optional.of(existing));
        doAnswer(inv -> {
            existing.setQuantity(dto.getQuantity());
            existing.setUnitPrice(dto.getUnitPrice());
            return null;
        }).when(orderItemMapper).updateEntityFromDto(dto, existing);

        when(orderItemDAO.save(existing)).thenReturn(updated);
        when(orderItemMapper.toDto(updated)).thenReturn(responseDTO);

        OrderItemResponseDTO result = orderItemService.update(1L, dto);

        assertEquals(3, result.getQuantity());
        verify(orderItemDAO).save(existing);
    }

    @Test
    void testDeleteById_Found() {
        when(orderItemDAO.findById(1L)).thenReturn(Optional.of(new OrderItem()));
        doNothing().when(orderItemDAO).deleteById(1L);
        orderItemService.deleteById(1L);
        verify(orderItemDAO).deleteById(1L);
    }

    @Test
    void testDeleteById_NotFound() {
        when(orderItemDAO.findById(99L)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> orderItemService.deleteById(99L));
    }
}
