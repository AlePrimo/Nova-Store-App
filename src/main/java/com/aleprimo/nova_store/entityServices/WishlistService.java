package com.aleprimo.nova_store.entityServices;


import com.aleprimo.nova_store.dto.whishlist.WishlistRequestDTO;
import com.aleprimo.nova_store.dto.whishlist.WishlistResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WishlistService {
    WishlistResponseDTO create(WishlistRequestDTO dto);
    WishlistResponseDTO getById(Long id);
    Page<WishlistResponseDTO> getAll(Pageable pageable);
    WishlistResponseDTO updateWishlist(Long id, WishlistRequestDTO requestDTO);
    void delete(Long id);
}
