package com.aleprimo.nova_store.persistence;



import com.aleprimo.nova_store.models.Address;

import java.util.List;
import java.util.Optional;

public interface AddressDAO {
    Address save(Address address);
    Optional<Address> findById(Long id);
    void deleteById(Long id);
    List<Address> findAll();
    boolean existById(Long id);
}
