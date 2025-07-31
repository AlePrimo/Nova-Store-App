package com.aleprimo.nova_store.persistence;



import com.aleprimo.nova_store.models.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface AddressDAO {
    Address save(Address address);
    Optional<Address> findById(Long id);
    void deleteById(Long id);
    Page<Address> findAll(Pageable page);
    boolean existById(Long id);
}
