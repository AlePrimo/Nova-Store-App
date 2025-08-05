package com.aleprimo.nova_store.repository;


import com.aleprimo.nova_store.models.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

Page<Order> findByCustomerId(Long id, Pageable pageable);

}

