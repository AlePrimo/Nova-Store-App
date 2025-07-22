package com.aleprimo.nova_store.entityServices;


import com.aleprimo.nova_store.dto.shoppingCart.ShoppingCartRequestDTO;
import com.aleprimo.nova_store.dto.shoppingCart.ShoppingCartResponseDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Tag(name = "Shopping Cart Service", description = "Operaciones relacionadas al carrito de compras")
public interface ShoppingCartService {

    ShoppingCartResponseDTO createCart(ShoppingCartRequestDTO dto);

    ShoppingCartResponseDTO getCartById(Long id);

    Page<ShoppingCartResponseDTO> getAllCarts(Pageable pageable);

    void deleteCart(Long id);
}
