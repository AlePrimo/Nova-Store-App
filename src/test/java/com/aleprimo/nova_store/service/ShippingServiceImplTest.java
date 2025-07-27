package com.aleprimo.nova_store.service;

import com.aleprimo.nova_store.controller.mappers.ShippingMapper;
import com.aleprimo.nova_store.dto.shipping.ShippingRequestDTO;
import com.aleprimo.nova_store.dto.shipping.ShippingResponseDTO;
import com.aleprimo.nova_store.entityServices.implementations.ShippingServiceImpl;
import com.aleprimo.nova_store.models.Order;
import com.aleprimo.nova_store.models.Shipping;
import com.aleprimo.nova_store.models.enums.OrderStatus;
import com.aleprimo.nova_store.models.enums.PaymentMethod;
import com.aleprimo.nova_store.persistence.OrderDAO;
import com.aleprimo.nova_store.persistence.ShippingDAO;
import com.aleprimo.nova_store.repository.OrderRepository;
import com.aleprimo.nova_store.repository.ShippingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShippingServiceImplTest {

    @Mock
    private ShippingDAO shippingDAO;

    @Mock
    private OrderDAO orderDAO;

    @Mock
    private ShippingRepository shippingRepository;

    @Mock
    private ShippingMapper shippingMapper;
@Mock
private OrderRepository orderRepository;
    @InjectMocks
    private ShippingServiceImpl shippingService;

    private Shipping shipping;
    private Order order;
    private ShippingRequestDTO requestDTO;
    private ShippingResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        order = Order.builder()
                .id(1L)
                .totalAmount(BigDecimal.valueOf(100))
                .paymentMethod(PaymentMethod.CASH)
                .orderStatus(OrderStatus.SHIPPED)
                .build();

        orderRepository.save(order);

        shipping = Shipping.builder()
                .order(order)
                .address("123 Calle")
                .city("Ciudad")
                .country("País")
                .postalCode("1234")
                .build();

        shippingRepository.save(shipping);

        requestDTO = ShippingRequestDTO.builder()
                .orderId(1L)
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
        when(orderDAO.findById(1L)).thenReturn(Optional.of(order));
        when(shippingMapper.toEntity(requestDTO)).thenReturn(shipping);
        when(shippingRepository.save(any(Shipping.class))).thenReturn(shipping);
        when(shippingMapper.toDto(any(Shipping.class))).thenReturn(responseDTO);

        ShippingResponseDTO result = shippingService.createShipping(requestDTO);

        assertEquals("123 Calle", result.getAddress());
        verify(shippingRepository).save(any(Shipping.class));
    }

    @Test
    void testGetShippingById() {
        when(shippingRepository.findById(1L)).thenReturn(Optional.of(shipping));
        when(shippingMapper.toDto(shipping)).thenReturn(responseDTO);

        ShippingResponseDTO result = shippingService.getShippingById(1L);

        assertEquals("123 Calle", result.getAddress());
        verify(shippingRepository).findById(1L);
    }

    @Test
    void testUpdateShipping() {
        when(shippingRepository.findById(1L)).thenReturn(Optional.of(shipping));
        doNothing().when(shippingMapper).updateEntityFromDto(requestDTO, shipping);
        when(shippingRepository.save(shipping)).thenReturn(shipping);
        when(shippingMapper.toDto(shipping)).thenReturn(responseDTO);

        ShippingResponseDTO result = shippingService.updateShipping(1L, requestDTO);

        assertEquals("123 Calle", result.getAddress());
        verify(shippingRepository).save(shipping);
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

        when(shippingDAO.findAll(pageable)).thenReturn(page);
        when(shippingMapper.toDto(shipping)).thenReturn(responseDTO);

        Page<ShippingResponseDTO> result = shippingService.getAllShippings(pageable);

        assertEquals(1, result.getTotalElements());
        verify(shippingDAO).findAll(pageable);
    }
}
