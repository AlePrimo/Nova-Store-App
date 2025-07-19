package com.aleprimo.nova_store.entityServices;


import com.aleprimo.nova_store.dto.orderItem.OrderItemRequestDTO;
import com.aleprimo.nova_store.dto.orderItem.OrderItemResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderItemService {
    Page<OrderItemResponseDTO> getAll(Pageable pageable);
    OrderItemResponseDTO getById(Long id);
    OrderItemResponseDTO create(OrderItemRequestDTO dto);
    OrderItemResponseDTO update(Long id, OrderItemRequestDTO dto);
    void deleteById(Long id);
}
