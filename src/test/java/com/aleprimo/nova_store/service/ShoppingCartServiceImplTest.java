package com.aleprimo.nova_store.service;


import com.aleprimo.nova_store.controller.mappers.ShoppingCartMapper;
import com.aleprimo.nova_store.dto.shoppingCart.ShoppingCartRequestDTO;
import com.aleprimo.nova_store.dto.shoppingCart.ShoppingCartResponseDTO;
import com.aleprimo.nova_store.models.Customer;
import com.aleprimo.nova_store.models.ShoppingCart;
import com.aleprimo.nova_store.persistence.CustomerDAO;
import com.aleprimo.nova_store.persistence.ShoppingCartDAO;
import com.aleprimo.nova_store.entityServices.implementations.ShoppingCartServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ShoppingCartServiceImplTest {

    @InjectMocks
    private ShoppingCartServiceImpl shoppingCartService;

    @Mock
    private ShoppingCartDAO shoppingCartDAO;

    @Mock
    private CustomerDAO customerDAO;

    @Mock
    private ShoppingCartMapper shoppingCartMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate() {
        ShoppingCartRequestDTO requestDTO = ShoppingCartRequestDTO.builder()
                .customerId(1L)
                .build();

        Customer customer = Customer.builder().id(1L).build();


        ShoppingCart cartToSave = new ShoppingCart();
        cartToSave.setCustomer(customer);

        ShoppingCart savedCart = ShoppingCart.builder()
                .id(100L)
                .customer(customer)
                .build();

        ShoppingCartResponseDTO responseDTO = ShoppingCartResponseDTO.builder()
                .id(100L)
                .build();

        when(customerDAO.findById(1L)).thenReturn(Optional.of(customer));
        when(shoppingCartDAO.save(any(ShoppingCart.class))).thenReturn(savedCart);
        when(shoppingCartMapper.toDto(savedCart)).thenReturn(responseDTO);

        ShoppingCartResponseDTO result = shoppingCartService.createCart(requestDTO);

        assertNotNull(result, "Debe devolver algo, no null");
        assertEquals(100L, result.getId());
    }

    @Test
    void testFindAllPaged() {
        Pageable pageable = PageRequest.of(0, 10);
        List<ShoppingCart> cartList = List.of(ShoppingCart.builder().id(1L).build());
        Page<ShoppingCart> cartPage = new PageImpl<>(cartList);

        when(shoppingCartDAO.findAll(pageable)).thenReturn(cartPage);
        when(shoppingCartMapper.toDto(any())).thenReturn(new ShoppingCartResponseDTO());

        Page<ShoppingCartResponseDTO> result = shoppingCartService.getAllCarts(pageable);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
    }

    @Test
    void testFindById() {
        ShoppingCart cart = ShoppingCart.builder().id(1L).build();
        when(shoppingCartDAO.findById(1L)).thenReturn(Optional.of(cart));
        when(shoppingCartMapper.toDto(cart)).thenReturn(new ShoppingCartResponseDTO());

        ShoppingCartResponseDTO result = shoppingCartService.getCartById(1L);

        assertNotNull(result);
    }

    @Test
    void testUpdate() {
        ShoppingCartRequestDTO requestDTO = new ShoppingCartRequestDTO();
        requestDTO.setCustomerId(1L);

        Customer customer = Customer.builder().id(1L).build();
        ShoppingCart cart = ShoppingCart.builder().id(1L).customer(customer).build();

        when(shoppingCartDAO.findById(1L)).thenReturn(Optional.of(cart));
        when(customerDAO.findById(1L)).thenReturn(Optional.of(customer));
        doNothing().when(shoppingCartMapper).updateEntityFromDto(requestDTO, cart);
        when(shoppingCartDAO.save(cart)).thenReturn(cart);
        when(shoppingCartMapper.toDto(cart)).thenReturn(new ShoppingCartResponseDTO());

        ShoppingCartResponseDTO result = shoppingCartService.update(1L, requestDTO);

        assertNotNull(result);
    }


    @Test
    void testDelete() {
        when(shoppingCartDAO.existsById(1L)).thenReturn(true);
        doNothing().when(shoppingCartDAO).deleteById(1L);

        assertDoesNotThrow(() -> shoppingCartService.deleteCart(1L));
        verify(shoppingCartDAO, times(1)).deleteById(1L);
    }
}
