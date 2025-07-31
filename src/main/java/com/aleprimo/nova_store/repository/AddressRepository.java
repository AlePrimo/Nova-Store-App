package com.aleprimo.nova_store.repository;


import com.aleprimo.nova_store.models.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
    Page<Address> findAllPage(Pageable pageable);
}
