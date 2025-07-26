package com.aleprimo.nova_store.repository;

import com.aleprimo.nova_store.models.Role;
import com.aleprimo.nova_store.models.enums.RoleName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    void shouldSaveAndFindByName() {
        Role role = Role.builder().name(RoleName.ROLE_ADMIN).build();
        roleRepository.save(role);

        Optional<Role> found = roleRepository.findByName(RoleName.ROLE_ADMIN);
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo(RoleName.ROLE_ADMIN);
    }
}
