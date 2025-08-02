package com.aleprimo.nova_store.service;


import com.aleprimo.nova_store.controller.mappers.WishlistMapper;
import com.aleprimo.nova_store.dto.whishlist.WishlistRequestDTO;
import com.aleprimo.nova_store.dto.whishlist.WishlistResponseDTO;
import com.aleprimo.nova_store.entityServices.implementations.WishlistServiceImpl;
import com.aleprimo.nova_store.models.Customer;
import com.aleprimo.nova_store.models.Product;
import com.aleprimo.nova_store.models.Wishlist;
import com.aleprimo.nova_store.persistence.WishlistDAO;

import com.aleprimo.nova_store.repository.CustomerRepository;
import com.aleprimo.nova_store.repository.WishlistRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WishlistServiceImplTest {

    @Mock
    private WishlistDAO wishlistDAO;

    @Mock
    private WishlistMapper wishlistMapper;

    @InjectMocks
    private WishlistServiceImpl wishlistService;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private WishlistRepository wishlistRepository;

    private WishlistRequestDTO requestDTO;
    private WishlistResponseDTO responseDTO;
    private Wishlist wishlist;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        Customer customer = Customer.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .isActive(true)
                .build();
customerRepository.save(customer);
        List<Product> products = List.of(
                Product.builder()
                        .id(1L)
                        .name("Producto 1")
                        .price(BigDecimal.valueOf(100))
                        .build(),
                Product.builder()
                        .id(2L)
                        .name("Producto 2")
                        .price(BigDecimal.valueOf(200))
                        .build()
        );

        wishlist = Wishlist.builder()
                .id(1L)
                .customer(customer)
                .products(products)
                .build();

        wishlistRepository.save(wishlist);

        requestDTO = WishlistRequestDTO.builder()
                .customerId(customer.getId())
                .productIds(products.stream().map(Product::getId).toList())
                .build();

        responseDTO = WishlistResponseDTO.builder()
                .id(wishlist.getId())
                .customerId(customer.getId())
                .productIds(products.stream().map(Product::getId).toList())
                .build();
    }

    @Test
    void testCreateWishlist() {
        when(wishlistDAO.save(wishlist)).thenReturn(wishlist);
        when(wishlistMapper.toDTO(wishlist)).thenReturn(responseDTO);


        WishlistResponseDTO result = wishlistService.create(requestDTO);

        assertThat(result).isEqualTo(responseDTO);
    }

    @Test
    void testGetWishlistById() {
        when(wishlistDAO.findById(1L)).thenReturn(Optional.ofNullable(wishlist));
        when(wishlistMapper.toDTO(wishlist)).thenReturn(responseDTO);
        when(wishlistDAO.findById(anyLong())).thenReturn(Optional.of(wishlist));

        WishlistResponseDTO result = wishlistService.getById(1L);

        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    void testGetAllWishlists() {
        Page<Wishlist> wishlistPage = new PageImpl<>(List.of(wishlist));
        when(wishlistDAO.findAll(any(Pageable.class))).thenReturn(wishlistPage);
        when(wishlistMapper.toDTO(wishlist)).thenReturn(responseDTO);
        when(wishlistDAO.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(List.of(wishlist)));
        Page<WishlistResponseDTO> result = wishlistService.getAll(PageRequest.of(0, 10));

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0)).isEqualTo(responseDTO);
    }

    @Test
    void testUpdateWishlist() {
        when(wishlistDAO.update(eq(1L), eq(requestDTO))).thenReturn(responseDTO);

        WishlistResponseDTO result = wishlistService.updateWishlist(1L, requestDTO);

        assertThat(result.getCustomerId()).isEqualTo(1L);
    }

    @Test
    void testDeleteWishlist() {
        doNothing().when(wishlistDAO).deleteById(1L);

        wishlistService.delete(1L);

        verify(wishlistDAO, times(1)).deleteById(1L);
    }
}