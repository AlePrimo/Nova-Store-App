package com.aleprimo.nova_store.service;


import com.aleprimo.nova_store.controller.mappers.ShoppingCartItemMapper;
import com.aleprimo.nova_store.dto.shoppingCartItem.ShoppingCartItemRequestDTO;
import com.aleprimo.nova_store.dto.shoppingCartItem.ShoppingCartItemResponseDTO;
import com.aleprimo.nova_store.entityServices.implementations.ShoppingCartItemServiceImpl;
import com.aleprimo.nova_store.models.Product;
import com.aleprimo.nova_store.models.ShoppingCart;
import com.aleprimo.nova_store.models.ShoppingCartItem;
import com.aleprimo.nova_store.persistence.ProductDAO;
import com.aleprimo.nova_store.persistence.ShoppingCartDAO;
import com.aleprimo.nova_store.persistence.ShoppingCartItemDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import jakarta.persistence.EntityNotFoundException;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShoppingCartItemServiceImplTest {

    @Mock
    private ShoppingCartItemDAO shoppingCartItemDAO;

    @Mock
    private ShoppingCartDAO shoppingCartDAO;

    @Mock
    private ProductDAO productDAO;

    @Mock
    private ShoppingCartItemMapper shoppingCartItemMapper;

    @InjectMocks
    private ShoppingCartItemServiceImpl shoppingCartItemService;

    private ShoppingCartItemRequestDTO requestDTO;
    private ShoppingCartItemResponseDTO responseDTO;
    private ShoppingCart cart;
    private Product product;
    private ShoppingCartItem item;

    @BeforeEach
    void setUp() {
        requestDTO = ShoppingCartItemRequestDTO.builder()
                .shoppingCartId(1L)
                .productId(2L)
                .quantity(3)
                .build();

        responseDTO = ShoppingCartItemResponseDTO.builder()
                .id(10L)
                .productName("Sample Product")
                .quantity(3)
                .price(BigDecimal.valueOf(300))
                .build();

        cart = ShoppingCart.builder().id(1L).build();
        product = Product.builder().id(2L).price(BigDecimal.valueOf(100)).build();
        item = ShoppingCartItem.builder().id(10L).shoppingCart(cart).product(product).quantity(3).price(BigDecimal.valueOf(300)).build();
    }

    @Test
    void testAddItemToCart() {
        when(shoppingCartDAO.findById(1L)).thenReturn(Optional.of(cart));
        when(productDAO.findById(2L)).thenReturn(Optional.of(product));
        when(shoppingCartItemMapper.toEntity(any())).thenReturn(item);
        when(shoppingCartItemDAO.save(any(ShoppingCartItem.class))).thenReturn(item);
        when(shoppingCartItemMapper.toDto(any())).thenReturn(responseDTO);

        ShoppingCartItemResponseDTO result = shoppingCartItemService.addItemToCart(requestDTO);

        assertNotNull(result);
        assertEquals("Sample Product", result.getProductName());
        verify(shoppingCartItemDAO, times(1)).save(any());
    }

    @Test
    void testDeleteItemFromCart() {
        when(shoppingCartItemDAO.existsById(10L)).thenReturn(true);

        shoppingCartItemService.deleteItem(10L);

        verify(shoppingCartItemDAO, times(1)).deleteById(10L);
    }

    @Test
    void testDeleteItemFromCartThrowsException() {
        when(shoppingCartItemDAO.existsById(99L)).thenReturn(false);

        assertThrows(jakarta.persistence.EntityNotFoundException.class, () ->
                shoppingCartItemService.deleteItem(99L));
    }
}
