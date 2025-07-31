package com.nova.store.service;


import com.aleprimo.nova_store.controller.mappers.AddressMapper;
import com.aleprimo.nova_store.dto.adress.AddressRequestDTO;
import com.aleprimo.nova_store.dto.adress.AddressResponseDTO;
import com.aleprimo.nova_store.entityServices.implementations.AddressServiceImpl;
import com.aleprimo.nova_store.models.Address;
import com.aleprimo.nova_store.models.Customer;
import com.aleprimo.nova_store.persistence.AddressDAO;
import com.aleprimo.nova_store.persistence.CustomerDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AddressServiceImplTest {

    @Mock
    private AddressDAO addressDAO;

    @Mock
    private AddressMapper addressMapper;

    @Mock
    private CustomerDAO customerDAO;

    @InjectMocks
    private AddressServiceImpl addressService;

    private Address address;
    private AddressRequestDTO requestDTO;
    private AddressResponseDTO responseDTO;
    private Customer customer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        customer = Customer.builder()
                .id(1L)
                .firstName("Juan")
                .lastName("Perez")
                .email("juan@mail.com")
                .isActive(true)
                .build();

        address = Address.builder()
                .id(1L)
                .street("Fake St")
                .city("Faketown")
                .province("FS")
                .postalCode("12345")
                .country("FC")
                .customer(customer)
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
        when(customerDAO.findById(1L)).thenReturn(Optional.of(customer));
        when(addressMapper.toEntity(requestDTO, customer)).thenReturn(address);
        when(addressDAO.save(address)).thenReturn(address);
        when(addressMapper.toDTO(address)).thenReturn(responseDTO);

        AddressResponseDTO result = addressService.createAddress(requestDTO);

        assertEquals(responseDTO, result);
        verify(addressDAO, times(1)).save(address);
    }

    @Test
    void testGetAllAddresses() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id").ascending());
        Page<Address> addressPage = new PageImpl<>(List.of(address), pageable, 1);

        when(addressDAO.findAllPage(pageable)).thenReturn(addressPage);
        when(addressMapper.toDTO(address)).thenReturn(responseDTO);

        Page<AddressResponseDTO> result = addressService.getAllAddresses(pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals(responseDTO, result.getContent().get(0));
        verify(addressDAO, times(1)).findAllPage(pageable);
    }

    @Test
    void testDeleteAddress() {
        when(addressDAO.findById(1L)).thenReturn(Optional.of(address));
        doNothing().when(addressDAO).deleteById(address.getId());

        addressService.deleteAddress(1L);

        verify(addressDAO, times(1)).deleteById(address.getId());
    }
}
