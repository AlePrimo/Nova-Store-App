package com.aleprimo.nova_store.entityServices.implementations;


import com.aleprimo.nova_store.controller.mappers.ShoppingCartMapper;

import com.aleprimo.nova_store.dto.shoppingCart.ShoppingCartRequestDTO;
import com.aleprimo.nova_store.dto.shoppingCart.ShoppingCartResponseDTO;

import com.aleprimo.nova_store.entityServices.ShoppingCartService;
import com.aleprimo.nova_store.handler.exceptions.ResourceNotFoundException;
import com.aleprimo.nova_store.models.Customer;

import com.aleprimo.nova_store.models.ShoppingCart;

import com.aleprimo.nova_store.persistence.CustomerDAO;

import com.aleprimo.nova_store.persistence.ShoppingCartDAO;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartDAO shoppingCartDAO;
    private final CustomerDAO customerDAO;
    private final ShoppingCartMapper mapper;

    @Override
    public ShoppingCartResponseDTO createCart(ShoppingCartRequestDTO dto) {
        Customer customer = customerDAO.findById(dto.getCustomerId())
                .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado"));

        ShoppingCart cart = new ShoppingCart();
        cart.setCustomer(customer);

        return mapper.toDto(shoppingCartDAO.save(cart));
    }

    @Override
    public ShoppingCartResponseDTO update(Long id, ShoppingCartRequestDTO dto) {
        ShoppingCart cart = shoppingCartDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Carrito no encontrado con id: " + id));

        mapper.updateEntityFromDto(dto, cart);
        return mapper.toDto(shoppingCartDAO.save(cart));
    }

    @Override
    public ShoppingCartResponseDTO getCartById(Long id) {
        return shoppingCartDAO.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Carrito no encontrado"));
    }

    @Override
    public Page<ShoppingCartResponseDTO> getAllCarts(Pageable pageable) {
        return shoppingCartDAO.findAll(pageable).map(mapper::toDto);
    }

    @Override
    public void deleteCart(Long id) {
        if (!shoppingCartDAO.existsById(id)) {
            throw new EntityNotFoundException("Carrito no encontrado");
        }
        shoppingCartDAO.deleteById(id);
    }

}
