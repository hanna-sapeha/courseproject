package com.hanna.sapeha.app.service.converter;

import com.hanna.sapeha.app.repository.model.Role;
import com.hanna.sapeha.app.repository.model.User;
import com.hanna.sapeha.app.repository.model.enums.RolesEnum;
import com.hanna.sapeha.app.service.model.UserDTO;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class UserConverterTest {
    private final UserConverter userConverter = new UserConverter();

    @Test
    void shouldConvertUserToDTOAndReturnRightId() {
        User user = getValidUser();
        long id = 1L;
        user.setId(id);
        UserDTO userDTO = userConverter.convert(user);
        assertEquals(id, userDTO.getId());
    }

    @Test
    void shouldConvertUserToDTOAndReturnRightFirstname() {
        User user = getValidUser();
        String firstname = "test";
        user.setFirstname(firstname);
        UserDTO userDTO = userConverter.convert(user);
        assertEquals(firstname, userDTO.getFirstname());
        assertNotEquals("firstname", userDTO.getFirstname());
    }

    @Test
    void shouldConvertUserToDTOAndReturnRightLastname() {
        User user = getValidUser();
        String lastname = "test";
        user.setLastname(lastname);
        UserDTO userDTO = userConverter.convert(user);
        assertEquals(lastname, userDTO.getLastname());
        assertNotEquals("lastname", userDTO.getLastname());
    }

    @Test
    void shouldConvertUserToDTOAndReturnRightPatronymic() {
        User user = getValidUser();
        String patronymic = "test";
        user.setPatronymic(patronymic);
        UserDTO userDTO = userConverter.convert(user);
        assertEquals(patronymic, userDTO.getPatronymic());
        assertNotEquals("patronymic", userDTO.getPatronymic());
    }

    @Test
    void shouldConvertUserToDTOAndReturnRightEmail() {
        User user = getValidUser();
        String email = "test";
        user.setEmail(email);
        UserDTO userDTO = userConverter.convert(user);
        assertEquals(email, userDTO.getEmail());
        assertNotEquals("email", userDTO.getEmail());
    }

    @Test
    void shouldConvertUserToDTOAndReturnRightRole() {
        User user = getValidUser();
        Role role = new Role();
        RolesEnum roleName = RolesEnum.SALE_USER;
        role.setRoleName(roleName);
        user.setRole(role);
        UserDTO userDTO = userConverter.convert(user);
        assertEquals(roleName.getDescription(), userDTO.getRoleName());
        assertNotEquals("SALE_USER", userDTO.getEmail());
    }

    @Test
    void shouldConvertDTOToUserAndReturnNullId() {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        User user = userConverter.convert(userDTO);
        assertNull(user.getId());
    }

    @Test
    void shouldConvertDTOToUserAndReturnNullUniqueNumber() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUniqueNumber(UUID.randomUUID());
        User user = userConverter.convert(userDTO);
        assertNull(user.getUniqueNumber());
    }

    @Test
    void shouldConvertDTOToUserAndReturnRightPassword() {
        UserDTO userDTO = new UserDTO();
        String password = "1234";
        userDTO.setPassword(password);
        User user = userConverter.convert(userDTO);
        assertEquals(password, user.getPassword());
    }

    @Test
    void shouldConvertDTOToUserAndReturnRightFirstname() {
        UserDTO userDTO = new UserDTO();
        String firstname = "firstname";
        userDTO.setFirstname(firstname);
        User user = userConverter.convert(userDTO);
        assertEquals(firstname, user.getFirstname());
    }

    @Test
    void shouldConvertDTOToUserAndReturnRightLastname() {
        UserDTO userDTO = new UserDTO();
        String lastname = "lastname";
        userDTO.setLastname(lastname);
        User user = userConverter.convert(userDTO);
        assertEquals(lastname, user.getLastname());
    }

    @Test
    void shouldConvertDTOToUserAndReturnRightPatronymic() {
        UserDTO userDTO = new UserDTO();
        String patronymic = "patronymic";
        userDTO.setPatronymic(patronymic);
        User user = userConverter.convert(userDTO);
        assertEquals(patronymic, user.getPatronymic());
    }

    private User getValidUser() {
        User user = new User();
        user.setId(1L);
        user.setFirstname("firstname");
        user.setLastname("lastname");
        user.setPatronymic("patronymic");
        user.setEmail("email");
        Role role = new Role();
        role.setRoleName(RolesEnum.ADMINISTRATOR);
        user.setRole(role);
        return user;
    }
}