package com.aleprimo.nova_store.controller.mappers;

import com.aleprimo.nova_store.dto.RoleDTO;
import com.aleprimo.nova_store.models.Role;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {

    public RoleDTO toDto(Role role) {
        if (role == null) {
            return null;
        }

        return RoleDTO.builder()
                .id(role.getId())
                .name(role.getName())
                .build();
    }


    public Role toEntity(RoleDTO roleDTO) {
        if (roleDTO == null) {
            return null;
        }

        return Role.builder()
                .id(roleDTO.getId())
                .name(roleDTO.getName())
                .build();
    }


}
