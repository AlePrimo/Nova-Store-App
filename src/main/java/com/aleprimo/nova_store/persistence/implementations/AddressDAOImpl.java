package com.aleprimo.nova_store.persistence.implementations;


import com.aleprimo.nova_store.models.Address;
import com.aleprimo.nova_store.persistence.AddressDAO;
import com.aleprimo.nova_store.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AddressDAOImpl implements AddressDAO {

    private final AddressRepository addressRepository;

    @Override
    public Address save(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public Optional<Address> findById(Long id) {
        return addressRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        addressRepository.deleteById(id);
    }

    @Override
    public Page<Address> findAllPage(Pageable pageable) {
        return addressRepository.findAllPage(pageable);
    }

    @Override
    public boolean existById(Long id) {
        return addressRepository.existsById(id);
    }
}
