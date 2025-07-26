package com.aleprimo.nova_store.repository;


import com.aleprimo.nova_store.models.Shipping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShippingRepository extends JpaRepository<Shipping, Long> {
    boolean existsById(Long id);
}
