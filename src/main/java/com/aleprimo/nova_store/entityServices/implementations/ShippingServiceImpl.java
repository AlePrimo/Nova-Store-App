package com.aleprimo.nova_store.entityServices.implementations;


import com.aleprimo.nova_store.dto.shipping.ShippingRequestDTO;
import com.aleprimo.nova_store.dto.shipping.ShippingResponseDTO;
import com.aleprimo.nova_store.entityServices.ShippingService;
import com.aleprimo.nova_store.handler.exceptions.ResourceNotFoundException;
import com.aleprimo.nova_store.models.Order;
import com.aleprimo.nova_store.models.Shipping;
import com.aleprimo.nova_store.models.enums.ShippingStatus;
import com.aleprimo.nova_store.persistence.OrderDAO;
import com.aleprimo.nova_store.persistence.ShippingDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ShippingServiceImpl implements ShippingService {

    private final ShippingDAO shippingDAO;
    private final OrderDAO orderDAO;
    private final ShippingMapper mapper;

    @Override
    public ShippingResponseDTO createShipping(ShippingRequestDTO dto) {
        Order order = orderDAO.findById(dto.getOrderId())
                .orElseThrow(() -> new EntityNotFoundException("Orden no encontrada"));

        Shipping shipping = Shipping.builder()
                .order(order)
                .address(dto.getAddress())
                .city(dto.getCity())
                .postalCode(dto.getPostalCode())
                .country(dto.getCountry())
                .status(ShippingStatus.PENDING)
                .shippedAt(null)
                .deliveredAt(null)
                .build();

        Shipping saved = shippingDAO.save(shipping);

        return mapper.toDto(saved);
    }

    @Override
    public ShippingResponseDTO updateShipping(Long id, ShippingRequestDTO dto) {
        Shipping shipping = shippingDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Shipping no encontrado con id: " + id));

        mapper.updateEntityFromDto(dto, shipping);

        Shipping updated = shippingDAO.save(shipping);
        return mapper.toDto(updated);
    }

    @Override
    public ShippingResponseDTO getShippingById(Long id) {
        return shippingDAO.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Shipping no encontrado"));
    }

    @Override
    public Page<ShippingResponseDTO> getAllShippings(Pageable pageable) {
        return shippingDAO.findAll(pageable).map(mapper::toDto);
    }

    @Override
    public void deleteShipping(Long id) {
        if (!shippingDAO.existById(id))
            throw new EntityNotFoundException("Shipping no encontrado");
        shippingDAO.deleteById(id);
    }
}
