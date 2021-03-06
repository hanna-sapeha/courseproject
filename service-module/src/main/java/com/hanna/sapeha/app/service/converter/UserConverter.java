package com.hanna.sapeha.app.service.converter;

import com.hanna.sapeha.app.repository.model.User;
import com.hanna.sapeha.app.service.exception.ServiceException;
import com.hanna.sapeha.app.service.model.UserDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@Log4j2
public class UserConverter {

    public UserDTO convert(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUniqueNumber(user.getUniqueNumber());
        userDTO.setFirstname(user.getFirstname());
        userDTO.setLastname(user.getLastname());
        userDTO.setPatronymic(user.getPatronymic());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        if (Objects.nonNull(user.getRole())) {
            userDTO.setRoleName(user.getRole().getRoleName().getDescription());
        } else {
            throw new ServiceException("User " + user.getEmail() + " does not have a role");
        }
        return userDTO;
    }

    public User convert(UserDTO userDTO) {
        User user = new User();
        user.setFirstname(userDTO.getFirstname());
        user.setLastname(userDTO.getLastname());
        user.setPatronymic(userDTO.getPatronymic());
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());
        return user;
    }

    public List<UserDTO> convert(List<User> users) {
        return users.stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }
}
