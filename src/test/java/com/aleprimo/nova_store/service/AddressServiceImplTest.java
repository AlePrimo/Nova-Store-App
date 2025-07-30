package com.aleprimo.nova_store.service;


import com.aleprimo.nova_store.controller.mappers.AddressMapper;
import com.aleprimo.nova_store.dto.adress.AddressRequestDTO;
import com.aleprimo.nova_store.dto.adress.AddressResponseDTO;
import com.aleprimo.nova_store.entityServices.implementations.AddressServiceImpl;
import com.aleprimo.nova_store.handler.exceptions.ResourceNotFoundException;
import com.aleprimo.nova_store.models.Address;
import com.aleprimo.nova_store.persistence.AddressDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AddressServiceImplTest {

    @InjectMocks
    private AddressServiceImpl addressService;

    @Mock
    private AddressDAO addressDAO;

    @Mock
    private AddressMapper addressMapper;

    private Address address;
    private AddressRequestDTO requestDTO;
    private AddressResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        address = Address.builder()
                .id(1L)
                .street("Fake St")
                .city("Faketown")
                .province("FS")
                .postalCode("12345")
                .country("FC")
                .build();

        requestDTO = AddressRequestDTO.builder()
                .street("Fake St")
                .city("Faketown")
                .province("FS")
                .postalCode("12345")
                .country("FC")
                .customerId(1L)
                .build();

        responseDTO = AddressResponseDTO.builder()
                .id(1L)
                .street("Fake St")
                .city("Faketown")
                .province("FS")
                .postalCode("12345")
                .country("FC")
                .build();
    }

    @Test
    void testCreateAddress() {
        when(addressMapper.toEntity(requestDTO)).thenReturn(address);
        when(addressDAO.save(address)).thenReturn(address);
        when(addressMapper.toDTO(address)).thenReturn(responseDTO);

        AddressResponseDTO result = addressService.createAddress(requestDTO);

        assertEquals(responseDTO, result);
        verify(addressDAO).save(address);
    }

    @Test
    void testGetAllAddresses() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Address> page = new PageImpl<>(List.of(address));

        when(addressDAO.findAll(pageable)).thenReturn(page);
        when(addressMapper.toDTO(address)).thenReturn(responseDTO);

        Page<AddressResponseDTO> result = addressService.getAllAddresses(pageable);

        assertEquals(1, result.getTotalElements());
        verify(addressDAO).findAll(pageable);
    }

    @Test
    void testGetAddressById_found() {
        when(addressDAO.findById(1L)).thenReturn(Optional.of(address));
        when(addressMapper.toDTO(address)).thenReturn(responseDTO);

        AddressResponseDTO result = addressService.getAddressById(1L);

        assertEquals(responseDTO, result);
    }

    @Test
    void testGetAddressById_notFound() {
        when(addressDAO.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> addressService.getAddressById(1L));
    }

    @Test
    void testDeleteAddress_found() {
        when(addressDAO.findById(1L)).thenReturn(Optional.of(address));
        doNothing().when(addressDAO).deleteById(1L);

        addressService.deleteAddress(1L);
        verify(addressDAO).deleteById(1L);
    }

    @Test
    void testDeleteAddress_notFound() {
        when(addressDAO.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> addressService.deleteAddress(1L));
    }
}
