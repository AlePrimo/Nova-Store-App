package com.aleprimo.nova_store.entityServices;

import com.aleprimo.nova_store.dto.ChangePasswordRequest;
import com.aleprimo.nova_store.models.enums.RoleName;
import com.aleprimo.nova_store.models.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserService {

    UserEntity save(UserEntity user);

    Optional<UserEntity> findById(Long id);

    Page<UserEntity> findAll(Pageable pageable);

    void deleteById(Long id);

    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Page<UserEntity> findByEnabledTrue(Pageable pageable);

    @Query("SELECT u FROM UserEntity u JOIN u.roles r WHERE r.name = :roleName")
    List<UserEntity> findByRoleName(@Param("roleName") RoleName roleName);

    void changePassword(String usernameOrEmail, ChangePasswordRequest request);


}
