package com.aleprimo.nova_store.persistence.implementations;


import com.aleprimo.nova_store.models.Shipping;
import com.aleprimo.nova_store.persistence.ShippingDAO;
import com.aleprimo.nova_store.repository.ShippingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ShippingDAOImpl implements ShippingDAO {

    private final ShippingRepository shippingRepository;

    @Override
    public Shipping save(Shipping shipping) {
        return shippingRepository.save(shipping);
    }

    @Override
    public Optional<Shipping> findById(Long id) {
        return shippingRepository.findById(id);
    }

    @Override
    public Page<Shipping> findAll(Pageable pageable) {
        return shippingRepository.findAll(pageable);
    }

    @Override
    public void deleteById(Long id) {
        shippingRepository.deleteById(id);
    }

    @Override
    public boolean existById(Long id) {
        return shippingRepository.existsById(id);
    }
}
