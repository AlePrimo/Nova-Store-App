package com.aleprimo.nova_store.entityServices.implementations;

import com.aleprimo.nova_store.controller.mappers.ShoppingCartItemMapper;
import com.aleprimo.nova_store.controller.mappers.ShoppingCartMapper;

import com.aleprimo.nova_store.dto.shoppingCart.ShoppingCartRequestDTO;
import com.aleprimo.nova_store.dto.shoppingCart.ShoppingCartResponseDTO;
import com.aleprimo.nova_store.dto.shoppingCartItem.ShoppingCartItemRequestDTO;
import com.aleprimo.nova_store.dto.shoppingCartItem.ShoppingCartItemResponseDTO;
import com.aleprimo.nova_store.entityServices.ShoppingCartItemService;
import com.aleprimo.nova_store.entityServices.ShoppingCartService;
import com.aleprimo.nova_store.handler.exceptions.ResourceNotFoundException;
import com.aleprimo.nova_store.models.Customer;
import com.aleprimo.nova_store.models.Product;
import com.aleprimo.nova_store.models.ShoppingCart;
import com.aleprimo.nova_store.models.ShoppingCartItem;
import com.aleprimo.nova_store.persistence.CustomerDAO;
import com.aleprimo.nova_store.persistence.ProductDAO;
import com.aleprimo.nova_store.persistence.ShoppingCartDAO;
import com.aleprimo.nova_store.persistence.ShoppingCartItemDAO;
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
        cart = shoppingCartDAO.save(cart);

        return mapper.toDto(cart);
    }

    @Override
    public ShoppingCartResponseDTO update(Long id, ShoppingCartRequestDTO dto) {
        ShoppingCart cart = shoppingCartDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ShoppingCart not found with id: " + id));

        mapper.updateEntityFromDto(dto, cart);
        ShoppingCart updated = shoppingCartDAO.save(cart);

        return mapper.toDto(updated);
    }

    @Override
    public ShoppingCartResponseDTO getCartById(Long id) {
        ShoppingCart cart = shoppingCartDAO.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Carrito no encontrado"));
        return mapper.toDto(cart);
    }

    @Override
    public Page<ShoppingCartResponseDTO> getAllCarts(Pageable pageable) {
        return shoppingCartDAO.findAll(pageable).map(mapper::toDto);
    }

    @Override
    public void deleteCart(Long id) {
        if (!shoppingCartDAO.existsById(id))
            throw new EntityNotFoundException("Carrito no encontrado");
        shoppingCartDAO.deleteById(id);
    }

    @Service
    @RequiredArgsConstructor
    public static class ShoppingCartItemServiceImpl implements ShoppingCartItemService {

        private final ShoppingCartItemDAO itemDAO;
        private final ShoppingCartDAO cartDAO;
        private final ProductDAO productDAO;
        private final ShoppingCartItemMapper mapper;

        @Override
        public ShoppingCartItemResponseDTO addItemToCart(ShoppingCartItemRequestDTO dto) {
            ShoppingCart cart = cartDAO.findById(dto.getShoppingCartId())
                    .orElseThrow(() -> new EntityNotFoundException("Carrito no encontrado"));
            Product product = productDAO.findById(dto.getProductId())
                    .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado"));

            ShoppingCartItem item = mapper.toEntity(dto);
            item.setShoppingCart(cart);
            item.setProduct(product);

            return mapper.toDto(itemDAO.save(item));
        }

        @Override
        public ShoppingCartItemResponseDTO update(Long id, ShoppingCartItemRequestDTO dto) {
            ShoppingCartItem item = itemDAO.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("ShoppingCartItem not found with id: " + id));

            mapper.updateEntityFromDto(dto, item);
            ShoppingCartItem updated = itemDAO.save(item);

            return mapper.toDto(updated);
        }

        @Override
        public ShoppingCartItemResponseDTO getItemById(Long id) {
            ShoppingCartItem item = itemDAO.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Ítem no encontrado"));
            return mapper.toDto(item);
        }

        @Override
        public Page<ShoppingCartItemResponseDTO> getAllItems(Pageable pageable) {
            return itemDAO.findAll(pageable).map(mapper::toDto);
        }

        @Override
        public void deleteItem(Long id) {
            if (!itemDAO.existsById(id))
                throw new EntityNotFoundException("Ítem no encontrado");
            itemDAO.deleteById(id);
        }
    }
}
