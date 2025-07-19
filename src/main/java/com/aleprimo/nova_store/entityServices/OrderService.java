package com.aleprimo.nova_store.entityServices;

import com.aleprimo.nova_store.dto.order.OrderRequestDTO;
import com.aleprimo.nova_store.dto.order.OrderResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    Page<OrderResponseDTO> getAll(Pageable pageable);
    OrderResponseDTO getById(Long id);
    OrderResponseDTO create(OrderRequestDTO dto);
    OrderResponseDTO update(Long id, OrderRequestDTO dto);
    void deleteById(Long id);
}
