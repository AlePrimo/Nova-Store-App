package com.aleprimo.nova_store.entityServices.implementations;

import com.aleprimo.nova_store.controller.mappers.ShoppingCartItemMapper;

import com.aleprimo.nova_store.dto.shoppingCartItem.ShoppingCartItemRequestDTO;
import com.aleprimo.nova_store.dto.shoppingCartItem.ShoppingCartItemResponseDTO;
import com.aleprimo.nova_store.entityServices.ShoppingCartItemService;
import com.aleprimo.nova_store.handler.exceptions.ResourceNotFoundException;
import com.aleprimo.nova_store.models.Product;
import com.aleprimo.nova_store.models.ShoppingCart;
import com.aleprimo.nova_store.models.ShoppingCartItem;
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
public class ShoppingCartItemServiceImpl implements ShoppingCartItemService {

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

    @Override
    public boolean existsByShoppingCartIdAndProductId(Long shoppingCartId, Long productId) {
        return itemDAO.existsByShoppingCartIdAndProductId(shoppingCartId, productId);
    }
}
