package com.aleprimo.nova_store.entityServices;


import com.aleprimo.nova_store.dto.adress.AddressRequestDTO;
import com.aleprimo.nova_store.dto.adress.AddressResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AddressService {
    AddressResponseDTO createAddress(AddressRequestDTO dto);
    AddressResponseDTO getAddressById(Long id);
    Page<AddressResponseDTO> getAllAddresses(Pageable pageable);
    AddressResponseDTO updateAddress(Long id, AddressRequestDTO dto);
    void deleteAddress(Long id);
}
