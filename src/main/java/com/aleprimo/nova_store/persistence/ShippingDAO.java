package com.aleprimo.nova_store.persistence;


import com.aleprimo.nova_store.models.Shipping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ShippingDAO {
    Shipping save(Shipping shipping);
    Optional<Shipping> findById(Long id);
    Page<Shipping> findAll(Pageable pageable);
    void deleteById(Long id);
    boolean existById(Long id);
}
