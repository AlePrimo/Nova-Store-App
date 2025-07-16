package com.aleprimo.nova_store.entityServices;

import com.aleprimo.nova_store.dto.customer.CustomerRequestDTO;
import com.aleprimo.nova_store.dto.customer.CustomerResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerService {
    Page<CustomerResponseDTO> getAll(Pageable pageable);
    Page<CustomerResponseDTO> getActive(Pageable pageable);
    CustomerResponseDTO getById(Long id);
    CustomerResponseDTO create(CustomerRequestDTO dto);
    CustomerResponseDTO update(Long id, CustomerRequestDTO dto);
    void delete(Long id);
}
