package com.aleprimo.nova_store.entityServices;

import com.aleprimo.nova_store.models.Role;
import com.aleprimo.nova_store.models.RoleName;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    Role save(Role role);

    Optional<Role> findById(Long id);

    Optional<Role> findByName(RoleName role);

    List<Role> findAll();

    void delete(Long id);
}
