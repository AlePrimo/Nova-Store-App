package com.aleprimo.nova_store.persistence.implementations;


import com.aleprimo.nova_store.models.Order;
import com.aleprimo.nova_store.persistence.OrderDAO;
import com.aleprimo.nova_store.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class OrderDAOImpl implements OrderDAO {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderDAOImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public Page<Order> findAll(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    @Override
    public void deleteById(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public Page<Order> findByCustomerId(Long id, Pageable pageable) {
        return orderRepository.findByCustomerId(id,pageable);
    }
}
