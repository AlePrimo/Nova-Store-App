package com.aleprimo.nova_store.service;


import com.aleprimo.nova_store.controller.mappers.ShippingMapper;
import com.aleprimo.nova_store.dto.shipping.ShippingRequestDTO;
import com.aleprimo.nova_store.dto.shipping.ShippingResponseDTO;
import com.aleprimo.nova_store.entityServices.implementations.ShippingServiceImpl;
import com.aleprimo.nova_store.models.Shipping;
import com.aleprimo.nova_store.repository.ShippingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class ShippingServiceImplTest {

    @Mock
    private ShippingRepository shippingRepository;

    @Mock
    private ShippingMapper shippingMapper;

    @InjectMocks
    private ShippingServiceImpl shippingService;

    private Shipping shipping;
    private ShippingRequestDTO requestDTO;
    private ShippingResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        shipping = Shipping.builder()
                .id(1L)
                .address("123 Calle")
                .city("Ciudad")
                .country("País")
                .postalCode("1234")
                .build();

        requestDTO = ShippingRequestDTO.builder()
                .address("123 Calle")
                .city("Ciudad")
                .country("País")
                .postalCode("1234")
                .build();

        responseDTO = ShippingResponseDTO.builder()
                .id(1L)
                .address("123 Calle")
                .city("Ciudad")
                .country("País")
                .postalCode("1234")
                .build();
    }

    @Test
    void testCreateShipping() {
        when(shippingMapper.toEntity(requestDTO)).thenReturn(shipping);
        when(shippingRepository.save(shipping)).thenReturn(shipping);
        when(shippingMapper.toDto(shipping)).thenReturn(responseDTO);

        ShippingResponseDTO result = shippingService.createShipping(requestDTO);

        assertEquals("123 Calle", result.getAddress());
        verify(shippingRepository).save(shipping);
    }

    @Test
    void testGetShippingById() {
        when(shippingRepository.findById(1L)).thenReturn(Optional.of(shipping));
        when(shippingMapper.toDto(shipping)).thenReturn(responseDTO);

        ShippingResponseDTO result = shippingService.getShippingById(1L);

        assertEquals("123 Calle", result.getAddress());
    }

    @Test
    void testUpdateShipping() {
        when(shippingRepository.findById(1L)).thenReturn(Optional.of(shipping));
        doNothing().when(shippingMapper).updateEntityFromDto(requestDTO, shipping);
        when(shippingRepository.save(shipping)).thenReturn(shipping);
        when(shippingMapper.toDto(shipping)).thenReturn(responseDTO);

        ShippingResponseDTO result = shippingService.updateShipping(1L, requestDTO);

        assertEquals("123 Calle", result.getAddress());
    }

    @Test
    void testDeleteShipping() {
        when(shippingRepository.findById(1L)).thenReturn(Optional.of(shipping));

        shippingService.deleteShipping(1L);

        verify(shippingRepository).delete(shipping);
    }

    @Test
    void testGetAllShippings() {
        PageRequest pageable = PageRequest.of(0, 10);
        Page<Shipping> page = new PageImpl<>(List.of(shipping));
        when(shippingRepository.findAll(pageable)).thenReturn(page);
        when(shippingMapper.toDto(any())).thenReturn(responseDTO);

        Page<ShippingResponseDTO> result = shippingService.getAllShippings(pageable);

        assertEquals(1, result.getTotalElements());
    }
}
