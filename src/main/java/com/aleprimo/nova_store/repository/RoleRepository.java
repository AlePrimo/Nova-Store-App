package com.aleprimo.nova_store.repository;

import com.aleprimo.nova_store.models.Role;
import com.aleprimo.nova_store.models.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByName(RoleName role);
}
