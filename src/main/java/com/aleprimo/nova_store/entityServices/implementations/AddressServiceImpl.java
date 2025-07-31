package com.aleprimo.nova_store.entityServices.implementations;


import com.aleprimo.nova_store.controller.mappers.AddressMapper;
import com.aleprimo.nova_store.dto.adress.AddressRequestDTO;
import com.aleprimo.nova_store.dto.adress.AddressResponseDTO;
import com.aleprimo.nova_store.entityServices.AddressService;
import com.aleprimo.nova_store.handler.exceptions.ResourceNotFoundException;
import com.aleprimo.nova_store.models.Address;
import com.aleprimo.nova_store.models.Customer;
import com.aleprimo.nova_store.persistence.AddressDAO;
import com.aleprimo.nova_store.persistence.CustomerDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressDAO addressDAO;
    private final CustomerDAO customerDAO;
    private final AddressMapper mapper;

    @Override
    public AddressResponseDTO createAddress(AddressRequestDTO dto) {
        Customer customer = customerDAO.findById(dto.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer no encontrado con id " + dto.getCustomerId()));

        Address address = mapper.toEntity(dto, customer);
        return mapper.toDTO(addressDAO.save(address));
    }

    @Override
    public AddressResponseDTO getAddressById(Long id) {
        Address address = addressDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Address no encontrada con id " + id));
        return mapper.toDTO(address);
    }

    @Override
    public Page<AddressResponseDTO> getAllAddresses(Pageable pageable) {
        return addressDAO.findAll(pageable).stream()
                .map(mapper::toDTO)
                .collect(java.util.stream.Collectors.collectingAndThen(
                        java.util.stream.Collectors.toList(),
                        list -> new org.springframework.data.domain.PageImpl<>(list, pageable, list.size())));
    }

    @Override
    public AddressResponseDTO updateAddress(Long id, AddressRequestDTO dto) {
        Address address = addressDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Address no encontrada con id " + id));

        Customer customer = customerDAO.findById(dto.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer no encontrado con id " + dto.getCustomerId()));

        address.setStreet(dto.getStreet());
        address.setCity(dto.getCity());
        address.setProvince(dto.getProvince());
        address.setPostalCode(dto.getPostalCode());
        address.setCountry(dto.getCountry());
        address.setCustomer(customer);

        return mapper.toDTO(addressDAO.save(address));
    }

    @Override
    public void deleteAddress(Long id) {
        Address address = addressDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Address no encontrada con id " + id));
        addressDAO.deleteById(id);
    }
}
