package com.aleprimo.nova_store.persistence.implementations;

import com.aleprimo.nova_store.models.enums.RoleName;
import com.aleprimo.nova_store.models.UserEntity;
import com.aleprimo.nova_store.persistence.UserDAO;
import com.aleprimo.nova_store.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class UserDAOImpl implements UserDAO {

    private final UserRepository userRepository;

    @Override
    public UserEntity save(UserEntity user) {
        return this.userRepository.save(user);
    }

    @Override
    public Optional<UserEntity> findById(Long id) {
        return this.userRepository.findById(id);
    }

    @Override
    public Page<UserEntity> findAll(Pageable pageable) {
        return this.userRepository.findAll(pageable);
    }


    @Override
    public void deleteById(Long id) {
        this.userRepository.deleteById(id);

    }

    @Override
    public Optional<UserEntity> findByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    @Override
    public boolean existsByUsername(String username) {
        return this.userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return this.userRepository.existsByEmail(email);
    }

    @Override
    public Page<UserEntity> findByEnabledTrue(Pageable pageable) {
        return this.userRepository.findByEnabledTrue(pageable);
    }


    @Override
    public List<UserEntity> findByRoleName(RoleName roleName) {
        return this.userRepository.findByRoleName(roleName);
    }
}
