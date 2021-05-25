package com.hanna.sapeha.app.service.converter;

import com.hanna.sapeha.app.repository.model.Role;
import com.hanna.sapeha.app.repository.model.enums.RolesEnum;
import com.hanna.sapeha.app.service.model.RoleDTO;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RoleConverterTest {
    private final RoleConverter roleConverter = new RoleConverter();

    @Test
    void shouldConvertRoleToDtoAndReturnNullObject() {
        Role role = new Role();
        RoleDTO roleDTO = roleConverter.convert(role);
        assertNotNull(roleDTO);
    }

    @Test
    void shouldConvertRoleToDtoAndReturnRightId() {
        Role role = new Role();
        long id = 1L;
        role.setId(id);
        RoleDTO roleDTO = roleConverter.convert(role);
        assertEquals(id, roleDTO.getId());
    }

    @Test
    void shouldConvertRoleToDtoAndReturnRightRoleEnumName() {
        Role role = new Role();
        RolesEnum admin = RolesEnum.ADMINISTRATOR;
        role.setRoleName(admin);
        RoleDTO roleDTO = roleConverter.convert(role);
        assertEquals(admin.name(), roleDTO.getRoleEnumName());
    }

    @Test
    void shouldConvertRoleToDtoAndReturnRightRoleName() {
        Role role = new Role();
        RolesEnum admin = RolesEnum.ADMINISTRATOR;
        role.setRoleName(admin);
        RoleDTO roleDTO = roleConverter.convert(role);
        assertEquals(admin.getDescription(), roleDTO.getRoleName());
    }

    @Test
    void shouldConvertReviewsToListDtoAndReturn() {
        Role role = new Role();
        long id = 1L;
        role.setId(id);
        RolesEnum admin = RolesEnum.ADMINISTRATOR;
        role.setRoleName(admin);
        List<Role> roles = Collections.singletonList(role);
        List<RoleDTO> roleDTOS = roleConverter.convert(roles);
        assertEquals(id, roleDTOS.get(0).getId());
        assertEquals(admin.name(), roleDTOS.get(0).getRoleEnumName());
    }
}