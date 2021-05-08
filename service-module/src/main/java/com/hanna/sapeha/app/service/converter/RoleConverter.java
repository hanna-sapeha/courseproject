package com.hanna.sapeha.app.service.converter;

import com.hanna.sapeha.app.repository.model.Role;
import com.hanna.sapeha.app.service.model.RoleDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RoleConverter {

    public List<RoleDTO> convert(List<Role> roles) {
        return roles.stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

    public RoleDTO convert(Role role) {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(role.getId());
        roleDTO.setRoleEnumName(role.getRoleName().name());
        roleDTO.setRoleName(role.getRoleName().getDescription());
        return roleDTO;
    }
}
