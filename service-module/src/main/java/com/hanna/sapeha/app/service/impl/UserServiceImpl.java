package com.hanna.sapeha.app.service.impl;

import com.hanna.sapeha.app.repository.RoleRepository;
import com.hanna.sapeha.app.repository.UserRepository;
import com.hanna.sapeha.app.repository.model.Role;
import com.hanna.sapeha.app.repository.model.User;
import com.hanna.sapeha.app.service.MailSender;
import com.hanna.sapeha.app.service.UserService;
import com.hanna.sapeha.app.service.converter.UserConverter;
import com.hanna.sapeha.app.service.exception.ServiceException;
import com.hanna.sapeha.app.service.model.PageDTO;
import com.hanna.sapeha.app.service.model.UserDTO;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.utility.RandomString;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.hanna.sapeha.app.service.constant.UserServiceConstants.PASSWORD_LENGTH;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final RoleRepository roleRepository;
    private final MailSender mailSender;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public PageDTO<UserDTO> getAllByPagination(Integer pageNumber, Integer pageSize) {
        PageDTO<UserDTO> page = new PageDTO<>();
        List<User> users = userRepository.findAll(pageNumber, pageSize);
        List<UserDTO> userDTOs = userConverter.convert(users);
        page.getObjects().addAll(userDTOs);
        Long countUsers = userRepository.getCountUsers();
        List<Integer> numbersOfPages = IntStream.rangeClosed(1, (int) Math.ceil((double) countUsers / pageSize))
                .boxed()
                .collect(Collectors.toList());
        page.getNumbersOfPage().addAll(numbersOfPages);
        return page;
    }

    @Override
    @Transactional
    public void add(UserDTO userDTO, Long idRole) {
        User userByEmail = userRepository.getUserByEmail(userDTO.getEmail());
        if (Objects.isNull(userByEmail)) {
            User user = userConverter.convert(userDTO);
            String password = RandomString.make(PASSWORD_LENGTH);
            String encodedPassword = passwordEncoder.encode(password);
            user.setPassword(encodedPassword);
            UUID uuid = UUID.randomUUID();
            user.setUniqueNumber(uuid);

            Role role = roleRepository.findById(idRole);
            if (Objects.nonNull(role)) {
                user.setRole(role);
            } else {
                throw new ServiceException("Role does not exist");
            }
            userRepository.persist(user);
            if (!user.getEmail().isBlank()) {
                mailSender.send(user.getEmail(), password);
            }
        } else {
            throw new ServiceException("User with email: " + userDTO.getEmail() + " already exist");
        }
    }

    @Override
    @Transactional
    public void removeById(Long id) {
        User user = userRepository.findById(id);
        if (Objects.nonNull(user)) {
            userRepository.remove(user);
        } else {
            throw new ServiceException("User does not exist");
        }
    }

    @Override
    @Transactional
    public void changePasswordById(Long id) {
        User user = userRepository.findById(id);
        if (Objects.nonNull(user)) {
            String newPassword = RandomString.make(PASSWORD_LENGTH);
            String newEncodedPassword = passwordEncoder.encode(newPassword);
            user.setPassword(newEncodedPassword);
            mailSender.send(user.getEmail(), newPassword);
        } else {
            throw new ServiceException("User does not exist");
        }
    }

    @Override
    @Transactional
    public void changeRoleById(Long idUser, Long idRole) {
        User user = userRepository.findById(idUser);
        if (Objects.nonNull(user)) {
            Role role = roleRepository.findById(idRole);
            user.setRole(role);
        } else {
            throw new ServiceException("User does not exist");
        }
    }
}
