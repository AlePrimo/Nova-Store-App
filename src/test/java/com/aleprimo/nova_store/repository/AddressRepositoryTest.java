package com.aleprimo.nova_store.repository;


import com.aleprimo.nova_store.models.Address;
import com.aleprimo.nova_store.models.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class AddressRepositoryTest {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private CustomerRepository customerRepository;

    private Customer customer;

    @BeforeEach
    void setUp() {
        customer = Customer.builder()
                .firstName("Juan")
                .lastName("Perez")
                .email("juan@mail.com")
                .isActive(true)
                .build();
        customerRepository.save(customer);
    }

    @Test
    void testSaveAndFindById() {
        Address address = Address.builder()
                .street("Av. Siempre Viva")
                .city("Springfield")
                .province("Missouri")
                .postalCode("1234")
                .country("USA")
                .customer(customer)
                .build();

        Address saved = addressRepository.save(address);
        Optional<Address> found = addressRepository.findById(saved.getId());

        assertTrue(found.isPresent());
        assertEquals("Springfield", found.get().getCity());
    }

    @Test
    void testFindAll() {
        Address a1 = Address.builder()
                .street("Calle 1").city("C1").province("S1")
                .postalCode("1000").country("AR")
                .customer(customer).build();

        Address a2 = Address.builder()
                .street("Calle 2").city("C2").province("S2")
                .postalCode("2000").country("AR")
                .customer(customer).build();

        addressRepository.saveAll(List.of(a1, a2));
        List<Address> all = addressRepository.findAll();

        assertEquals(2, all.size());
    }

    @Test
    void testDelete() {
        Address address = Address.builder()
                .street("Fake St")
                .city("Nowhere")
                .province("NA")
                .postalCode("0000")
                .country("US")
                .customer(customer)
                .build();

        Address saved = addressRepository.save(address);
        addressRepository.deleteById(saved.getId());

        Optional<Address> deleted = addressRepository.findById(saved.getId());
        assertTrue(deleted.isEmpty());
    }
}
