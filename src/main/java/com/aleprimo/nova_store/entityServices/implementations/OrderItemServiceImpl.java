package com.aleprimo.nova_store.entityServices.implementations;


import com.aleprimo.nova_store.controller.mappers.OrderItemMapper;
import com.aleprimo.nova_store.dto.orderItem.OrderItemRequestDTO;
import com.aleprimo.nova_store.dto.orderItem.OrderItemResponseDTO;
import com.aleprimo.nova_store.entityServices.OrderItemService;
import com.aleprimo.nova_store.models.Order;
import com.aleprimo.nova_store.models.OrderItem;
import com.aleprimo.nova_store.models.Product;
import com.aleprimo.nova_store.persistence.OrderDAO;
import com.aleprimo.nova_store.persistence.OrderItemDAO;
import com.aleprimo.nova_store.persistence.ProductDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemDAO orderItemDAO;
    private final OrderDAO orderDAO;
    private final ProductDAO productDAO;
    private final OrderItemMapper orderItemMapper;

    @Override
    public Page<OrderItemResponseDTO> getAll(Pageable pageable) {
        return orderItemDAO.findAll(pageable).map(orderItemMapper::toDto);
    }

    @Override
    public OrderItemResponseDTO getById(Long id) {
        return orderItemDAO.findById(id)
                .map(orderItemMapper::toDto)
                .orElseThrow(() -> new NoSuchElementException("Item de orden no encontrado con ID: " + id));
    }

    @Override
    public OrderItemResponseDTO create(OrderItemRequestDTO dto) {
        OrderItem item = orderItemMapper.toEntity(dto);

        Order order = orderDAO.findById(dto.getOrderId())
                .orElseThrow(() -> new NoSuchElementException("Orden no encontrada con ID: " + dto.getOrderId()));

        Product product = productDAO.findById(dto.getProductId())
                .orElseThrow(() -> new NoSuchElementException("Producto no encontrado con ID: " + dto.getProductId()));

        item.setOrder(order);
        item.setProduct(product);

        return orderItemMapper.toDto(orderItemDAO.save(item));
    }

    @Override
    public OrderItemResponseDTO update(Long id, OrderItemRequestDTO dto) {
        OrderItem item = orderItemDAO.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Item de orden no encontrado con ID: " + id));

        orderItemMapper.updateEntityFromDto(dto, item);
        return orderItemMapper.toDto(orderItemDAO.save(item));
    }

    @Override
    public void deleteById(Long id) {
        if (orderItemDAO.findById(id).isEmpty()) {
            throw new NoSuchElementException("Item de orden no encontrado con ID: " + id);
        }
        orderItemDAO.deleteById(id);
    }
}
