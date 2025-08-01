package com.aleprimo.nova_store.entityServices.implementations;


import com.aleprimo.nova_store.controller.mappers.WishlistMapper;
import com.aleprimo.nova_store.dto.whishlist.WishlistRequestDTO;
import com.aleprimo.nova_store.dto.whishlist.WishlistResponseDTO;
import com.aleprimo.nova_store.entityServices.WishlistService;
import com.aleprimo.nova_store.handler.exceptions.ResourceNotFoundException;
import com.aleprimo.nova_store.models.Customer;
import com.aleprimo.nova_store.models.Product;
import com.aleprimo.nova_store.models.Wishlist;
import com.aleprimo.nova_store.persistence.CustomerDAO;
import com.aleprimo.nova_store.persistence.ProductDAO;
import com.aleprimo.nova_store.persistence.WishlistDAO;
import com.aleprimo.nova_store.repository.CustomerRepository;
import com.aleprimo.nova_store.repository.ProductRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class WishlistServiceImpl implements WishlistService {

    private final WishlistDAO wishlistDAO;
    private final WishlistMapper wishlistMapper;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final CustomerDAO customerDAO;
    private final ProductDAO productDAO;

    @Override
    public WishlistResponseDTO create(WishlistRequestDTO dto) {
        Customer customer = customerRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        List<Product> products = productRepository.findAllById(dto.getProductIds());

        return wishlistMapper.toDTO(
                wishlistDAO.save(wishlistMapper.toEntity(dto, customer, products))
        );
    }

    @Override
    public WishlistResponseDTO getById(Long id) {
        return wishlistDAO.findById(id)
                .map(wishlistMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Wishlist not found"));
    }

    @Override
    public Page<WishlistResponseDTO> getAll(Pageable pageable) {
        return wishlistDAO.findAll(pageable).map(wishlistMapper::toDTO);
    }

    @Override
    public WishlistResponseDTO updateWishlist(Long id, WishlistRequestDTO requestDTO) {
        Wishlist wishlist = wishlistDAO.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Wishlist not found"));

        Customer customer = customerDAO.findById(requestDTO.getCustomerId())
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));

        List<Product> products = requestDTO.getProductIds().stream()
                .map(productId -> productDAO.findById(productId)
                        .orElseThrow(() -> new EntityNotFoundException("Product not found with ID: " + productId)))
                .toList();

        wishlist.setName(requestDTO.getName());
        wishlist.setCustomer(customer);
        wishlist.setProducts(products);

        return wishlistMapper.toDTO(wishlistDAO.save(wishlist));
    }

    @Override
    public void delete(Long id) {
        wishlistDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Wishlist not found"));
        wishlistDAO.deleteById(id);
    }
}
