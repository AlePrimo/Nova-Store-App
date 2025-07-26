package com.aleprimo.nova_store.entityServices;


import com.aleprimo.nova_store.dto.shipping.ShippingRequestDTO;
import com.aleprimo.nova_store.dto.shipping.ShippingResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ShippingService {
    ShippingResponseDTO createShipping(ShippingRequestDTO dto);
    ShippingResponseDTO updateShipping(Long id, ShippingRequestDTO dto);
    ShippingResponseDTO getShippingById(Long id);
    Page<ShippingResponseDTO> getAllShippings(Pageable pageable);
    void deleteShipping(Long id);
}
