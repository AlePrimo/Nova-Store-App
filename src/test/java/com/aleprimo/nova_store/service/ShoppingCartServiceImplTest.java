package com.aleprimo.nova_store.service;

import com.novastore.dto.request.ShoppingCartRequestDTO;
import com.novastore.dto.response.ShoppingCartResponseDTO;
import com.novastore.entity.Customer;
import com.novastore.entity.ShoppingCart;
import com.novastore.mapper.ShoppingCartMapper;
import com.novastore.repository.ShoppingCartRepository;
import com.novastore.service.dao.ShoppingCartDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ShoppingCartServiceImplTest {

    @Mock
    private ShoppingCartDAO shoppingCartDAO;

    @Mock
    private ShoppingCartMapper shoppingCartMapper;

    @InjectMocks
    private ShoppingCartServiceImpl shoppingCartService;

    private ShoppingCart shoppingCart;
    private ShoppingCartResponseDTO responseDTO;
    private ShoppingCartRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        Customer customer = Customer.builder().id(1L).build();

        shoppingCart = ShoppingCart.builder()
                .id(1L)
                .customer(customer)
                .totalPrice(BigDecimal.TEN)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        responseDTO = ShoppingCartResponseDTO.builder()
                .id(1L)
                .customerId(1L)
                .totalPrice(BigDecimal.TEN)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        requestDTO = ShoppingCartRequestDTO.builder()
                .customerId(1L)
                .totalPrice(BigDecimal.TEN)
                .build();
    }

    @Test
    void testFindById() {
        when(shoppingCartDAO.findById(1L)).thenReturn(Optional.of(shoppingCart));
        when(shoppingCartMapper.toDto(shoppingCart)).thenReturn(responseDTO);

        ShoppingCartResponseDTO result = shoppingCartService.findById(1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testFindAll() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<ShoppingCart> page = new PageImpl<>(List.of(shoppingCart));

        when(shoppingCartDAO.findAll(pageable)).thenReturn(page);
        when(shoppingCartMapper.toDto(any())).thenReturn(responseDTO);

        Page<ShoppingCartResponseDTO> result = shoppingCartService.findAll(pageable);

        assertEquals(1, result.getContent().size());
        verify(shoppingCartDAO).findAll(pageable);
    }

    @Test
    void testCreate() {
        when(shoppingCartMapper.toEntity(requestDTO)).thenReturn(shoppingCart);
        when(shoppingCartDAO.save(shoppingCart)).thenReturn(shoppingCart);
        when(shoppingCartMapper.toDto(shoppingCart)).thenReturn(responseDTO);

        ShoppingCartResponseDTO result = shoppingCartService.create(requestDTO);

        assertEquals(responseDTO.getId(), result.getId());
        verify(shoppingCartDAO).save(shoppingCart);
    }

    @Test
    void testUpdate() {
        when(shoppingCartDAO.findById(1L)).thenReturn(Optional.of(shoppingCart));
        doNothing().when(shoppingCartMapper).updateEntityFromDto(requestDTO, shoppingCart);
        when(shoppingCartDAO.save(shoppingCart)).thenReturn(shoppingCart);
        when(shoppingCartMapper.toDto(shoppingCart)).thenReturn(responseDTO);

        ShoppingCartResponseDTO result = shoppingCartService.update(1L, requestDTO);

        assertEquals(1L, result.getId());
    }

    @Test
    void testDelete() {
        doNothing().when(shoppingCartDAO).deleteById(1L);
        shoppingCartService.delete(1L);
        verify(shoppingCartDAO).deleteById(1L);
    }
}
