package com.aleprimo.nova_store.service;

import com.aleprimo.nova_store.controller.mappers.WishlistMapper;
import com.aleprimo.nova_store.dto.whishlist.WishlistRequestDTO;
import com.aleprimo.nova_store.dto.whishlist.WishlistResponseDTO;
import com.aleprimo.nova_store.entityServices.implementations.WishlistServiceImpl;
import com.aleprimo.nova_store.handler.exceptions.ResourceNotFoundException;
import com.aleprimo.nova_store.models.Customer;
import com.aleprimo.nova_store.models.Product;
import com.aleprimo.nova_store.models.Wishlist;
import com.aleprimo.nova_store.persistence.CustomerDAO;
import com.aleprimo.nova_store.persistence.ProductDAO;
import com.aleprimo.nova_store.persistence.WishlistDAO;
import com.aleprimo.nova_store.repository.CustomerRepository;
import com.aleprimo.nova_store.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class WishlistServiceImplTest {

    @InjectMocks
    private WishlistServiceImpl wishlistService;

    @Mock
    private WishlistDAO wishlistDAO;

    @Mock
    private WishlistMapper wishlistMapper;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CustomerDAO customerDAO;

    @Mock
    private ProductDAO productDAO;

    private WishlistRequestDTO requestDTO;
    private WishlistResponseDTO responseDTO;
    private Customer customer;
    private List<Product> products;
    private Wishlist wishlist;

    @BeforeEach
    void setUp() {
        customer = new Customer();
        customer.setId(1L);

        Product product = new Product();
        product.setId(1L);
        products = List.of(product);

        requestDTO = new WishlistRequestDTO();
        requestDTO.setCustomerId(1L);
        requestDTO.setProductIds(List.of(1L));

        wishlist = new Wishlist();
        wishlist.setId(1L);
        wishlist.setCustomer(customer);
        wishlist.setProducts(products);

        responseDTO = new WishlistResponseDTO();
        responseDTO.setId(1L);
    }

    @Test
    void create_ShouldReturnWishlistResponseDTO() {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(productRepository.findAllById(Set.of(1L))).thenReturn(products);
        when(wishlistMapper.toEntity(requestDTO, customer, products)).thenReturn(wishlist);
        when(wishlistDAO.save(wishlist)).thenReturn(wishlist);
        when(wishlistMapper.toDTO(wishlist)).thenReturn(responseDTO);

        WishlistResponseDTO result = wishlistService.create(requestDTO);

        assertEquals(1L, result.getId());
        verify(wishlistDAO).save(wishlist);
    }

    @Test
    void create_ShouldThrowException_WhenCustomerNotFound() {
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> wishlistService.create(requestDTO));
    }

    @Test
    void getById_ShouldReturnWishlistResponseDTO() {
        when(wishlistDAO.findById(1L)).thenReturn(Optional.of(wishlist));
        when(wishlistMapper.toDTO(wishlist)).thenReturn(responseDTO);

        WishlistResponseDTO result = wishlistService.getById(1L);

        assertEquals(1L, result.getId());
        verify(wishlistDAO).findById(1L);
    }

    @Test
    void getById_ShouldThrowException_WhenNotFound() {
        when(wishlistDAO.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> wishlistService.getById(1L));
    }

    @Test
    void getAll_ShouldReturnPageOfDTOs() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Wishlist> page = new PageImpl<>(List.of(wishlist));
        when(wishlistDAO.findAll(pageable)).thenReturn(page);
        when(wishlistMapper.toDTO(wishlist)).thenReturn(responseDTO);

        Page<WishlistResponseDTO> result = wishlistService.getAll(pageable);

        assertEquals(1, result.getContent().size());
        assertEquals(1L, result.getContent().get(0).getId());
    }

    @Test
    void update_ShouldReturnUpdatedDTO() {
        when(wishlistDAO.findById(1L)).thenReturn(Optional.of(wishlist));
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(productRepository.findAllById(Set.of(1L))).thenReturn(products);
        when(wishlistDAO.save(wishlist)).thenReturn(wishlist);
        when(wishlistMapper.toDTO(wishlist)).thenReturn(responseDTO);

        WishlistResponseDTO result = wishlistService.updateWishlist(1L, requestDTO);

        assertEquals(1L, result.getId());
    }

    @Test
    void update_ShouldThrowException_WhenWishlistNotFound() {
        when(wishlistDAO.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> wishlistService.updateWishlist(1L, requestDTO));
    }

    @Test
    void delete_ShouldCallDAODelete() {
        when(wishlistDAO.findById(1L)).thenReturn(Optional.of(wishlist));

        wishlistService.delete(1L);

        verify(wishlistDAO).deleteById(wishlist.getId());
    }

    @Test
    void delete_ShouldThrowException_WhenNotFound() {
        when(wishlistDAO.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> wishlistService.delete(1L));
    }
}
