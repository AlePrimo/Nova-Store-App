package com.aleprimo.nova_store.persistence.implementations;

import com.aleprimo.nova_store.models.Role;
import com.aleprimo.nova_store.models.enums.RoleName;
import com.aleprimo.nova_store.persistence.RoleDAO;
import com.aleprimo.nova_store.repository.RoleRepository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class RoleDAOImpl implements RoleDAO {

    private final RoleRepository roleRepository;

    public RoleDAOImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role save(Role role) {
        return this.roleRepository.save(role);
    }

    @Override
    public Optional<Role> findById(Long id) {
        return this.roleRepository.findById(id);
    }

    @Override
    public Optional<Role> findByName(RoleName role) {
        return this.roleRepository.findByName(role);
    }

    @Override
    public List<Role> findAll() {
        return this.roleRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        this.roleRepository.deleteById(id);
    }
}
