package com.aleprimo.nova_store.entityServices;


import com.aleprimo.nova_store.dto.shoppingCartItem.ShoppingCartItemRequestDTO;
import com.aleprimo.nova_store.dto.shoppingCartItem.ShoppingCartItemResponseDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Tag(name = "Shopping Cart Item Service", description = "Operaciones relacionadas a ítems del carrito")
public interface ShoppingCartItemService {

    ShoppingCartItemResponseDTO addItemToCart(ShoppingCartItemRequestDTO dto);
    ShoppingCartItemResponseDTO update(Long id, ShoppingCartItemRequestDTO dto);
    ShoppingCartItemResponseDTO getItemById(Long id);

    Page<ShoppingCartItemResponseDTO> getAllItems(Pageable pageable);

    void deleteItem(Long id);
}
