package com.aleprimo.nova_store.entityServices.implementations;

import com.aleprimo.nova_store.controller.mappers.CustomerMapper;
import com.aleprimo.nova_store.dto.customer.CustomerRequestDTO;
import com.aleprimo.nova_store.dto.customer.CustomerResponseDTO;
import com.aleprimo.nova_store.entityServices.CustomerService;
import com.aleprimo.nova_store.models.Customer;
import com.aleprimo.nova_store.persistence.CustomerDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerDAO customerDAO;
    private final CustomerMapper mapper;

    @Override
    public Page<CustomerResponseDTO> getAll(Pageable pageable) {
        return customerDAO.findAll(pageable).map(mapper::toDto);
    }

    @Override
    public Page<CustomerResponseDTO> getActive(Pageable pageable) {
        return customerDAO.findActive(pageable).map(mapper::toDto);
    }

    @Override
    public CustomerResponseDTO getById(Long id) {
        Customer customer = customerDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con id: " + id));
        return mapper.toDto(customer);
    }

    @Override
    public CustomerResponseDTO create(CustomerRequestDTO dto) {
        Customer saved = customerDAO.save(mapper.toEntity(dto));
        return mapper.toDto(saved);
    }

    @Override
    public CustomerResponseDTO update(Long id, CustomerRequestDTO dto) {
        Customer customer = customerDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con id: " + id));
        mapper.updateEntityFromDto(dto, customer);
        return mapper.toDto(customerDAO.save(customer));
    }

    @Override
    public void delete(Long id) {
        customerDAO.deleteById(id);
    }
}
