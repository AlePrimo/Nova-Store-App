package com.aleprimo.nova_store.entityServices.implementations;


import com.aleprimo.nova_store.controller.mappers.OrderMapper;
import com.aleprimo.nova_store.dto.order.OrderRequestDTO;
import com.aleprimo.nova_store.dto.order.OrderResponseDTO;

import com.aleprimo.nova_store.entityServices.OrderService;
import com.aleprimo.nova_store.models.Customer;
import com.aleprimo.nova_store.models.Order;
import com.aleprimo.nova_store.persistence.CustomerDAO;
import com.aleprimo.nova_store.persistence.OrderDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderDAO orderDAO;
    private final CustomerDAO customerDAO;
    private final OrderMapper orderMapper;

    @Override
    public Page<OrderResponseDTO> getAll(Pageable pageable) {
        return orderDAO.findAll(pageable).map(orderMapper::toDto);
    }

    @Override
    public OrderResponseDTO getById(Long id) {
        return orderDAO.findById(id)
                .map(orderMapper::toDto)
                .orElseThrow(() -> new NoSuchElementException("Orden no encontrada con ID: " + id));
    }

    @Override
    public OrderResponseDTO create(OrderRequestDTO dto) {
        Order order = orderMapper.toEntity(dto);
        Customer customer = customerDAO.findById(dto.getCustomerId())
                .orElseThrow(() -> new NoSuchElementException("Cliente no encontrado con ID: " + dto.getCustomerId()));
        order.setCustomer(customer);
        return orderMapper.toDto(orderDAO.save(order));
    }

    @Override
    public OrderResponseDTO update(Long id, OrderRequestDTO dto) {
        Order order = orderDAO.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Orden no encontrada con ID: " + id));
        orderMapper.updateEntityFromDto(dto, order);
        return orderMapper.toDto(orderDAO.save(order));
    }

    @Override
    public void deleteById(Long id) {
        if (orderDAO.findById(id).isEmpty()) {
            throw new NoSuchElementException("Orden no encontrada con ID: " + id);
        }
        orderDAO.deleteById(id);
    }
}
