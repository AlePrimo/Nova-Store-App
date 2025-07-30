package com.aleprimo.nova_store.controller.mappers;


import com.aleprimo.nova_store.dto.adress.AddressRequestDTO;
import com.aleprimo.nova_store.dto.adress.AddressResponseDTO;
import com.aleprimo.nova_store.models.Address;
import com.aleprimo.nova_store.models.Customer;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {

    public Address toEntity(AddressRequestDTO dto, Customer customer) {
        return Address.builder()
                .street(dto.getStreet())
                .city(dto.getCity())
                .province(dto.getProvince())
                .postalCode(dto.getPostalCode())
                .country(dto.getCountry())
                .customer(customer)
                .build();
    }

    public AddressResponseDTO toDTO(Address address) {
        return AddressResponseDTO.builder()
                .id(address.getId())
                .street(address.getStreet())
                .city(address.getCity())
                .province(address.getProvince())
                .postalCode(address.getPostalCode())
                .country(address.getCountry())
                .customerId(address.getCustomer().getId())
                .build();
    }
}
