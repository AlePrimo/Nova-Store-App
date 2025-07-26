package com.aleprimo.nova_store.service;

import com.aleprimo.nova_store.models.Role;
import com.aleprimo.nova_store.models.enums.RoleName;
import com.aleprimo.nova_store.persistence.RoleDAO;
import com.aleprimo.nova_store.entityServices.implementations.RoleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.test.annotation.DirtiesContext;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;

@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
class RoleServiceImplTest {

    @Mock
    private RoleDAO roleDAO;

    @InjectMocks
    private RoleServiceImpl roleService;

    private Role role;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        role = Role.builder()
                .id(1L)
                .name(RoleName.ROLE_USER)
                .build();
    }

    @Test
    void shouldSaveRole() {
        when(roleDAO.save(role)).thenReturn(role);

        Role saved = roleService.save(role);

        assertThat(saved).isNotNull();
        assertThat(saved.getName()).isEqualTo(RoleName.ROLE_USER);
        verify(roleDAO, times(1)).save(role);
    }

    @Test
    void shouldFindRoleById() {
        when(roleDAO.findById(1L)).thenReturn(Optional.of(role));

        Optional<Role> result = roleService.findById(1L);

        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo(RoleName.ROLE_USER);
        verify(roleDAO, times(1)).findById(1L);
    }

    @Test
    void shouldNotFindRoleById() {
        when(roleDAO.findById(1L)).thenReturn(Optional.empty());

        Optional<Role> result = roleService.findById(1L);

        assertThat(result).isNotPresent();
        verify(roleDAO).findById(1L);
    }

    @Test
    void shouldFindRoleByName() {
        when(roleDAO.findByName(RoleName.ROLE_USER)).thenReturn(Optional.of(role));

        Optional<Role> result = roleService.findByName(RoleName.ROLE_USER);

        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo(RoleName.ROLE_USER);
        verify(roleDAO).findByName(RoleName.ROLE_USER);
    }

    @Test
    void shouldFindAllRoles() {
        List<Role> roles = List.of(role);
        when(roleDAO.findAll()).thenReturn(roles);

        List<Role> result = roleService.findAll();

        assertThat(result).hasSize(1);
        verify(roleDAO).findAll();
    }

    @Test
    void shouldDeleteRole() {
        doNothing().when(roleDAO).delete(1L);

        roleService.delete(1L);

        verify(roleDAO, times(1)).delete(1L);
    }
}
