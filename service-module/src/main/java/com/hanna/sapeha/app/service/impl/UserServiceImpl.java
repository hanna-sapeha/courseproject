package com.hanna.sapeha.app.service.impl;

import com.hanna.sapeha.app.repository.RoleRepository;
import com.hanna.sapeha.app.repository.UserRepository;
import com.hanna.sapeha.app.repository.model.Role;
import com.hanna.sapeha.app.repository.model.User;
import com.hanna.sapeha.app.repository.model.enums.RolesEnum;
import com.hanna.sapeha.app.service.MailSender;
import com.hanna.sapeha.app.service.UserService;
import com.hanna.sapeha.app.service.converter.UserConverter;
import com.hanna.sapeha.app.service.exception.ServiceException;
import com.hanna.sapeha.app.service.model.PageDTO;
import com.hanna.sapeha.app.service.model.UserDTO;
import com.hanna.sapeha.app.service.util.ServiceUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
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
@Log4j2
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final RoleRepository roleRepository;
    private final MailSender mailSender;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public PageDTO<UserDTO> getAllByPagination(int pageNumber, int pageSize) {
        PageDTO<UserDTO> page = new PageDTO<>();
        List<User> users = userRepository.findAll(pageNumber, pageSize);
        List<UserDTO> userDTOs = userConverter.convert(users);
        page.getObjects().addAll(userDTOs);
        Long countUsers = userRepository.getCount();
        List<Integer> numbersOfPages = IntStream.rangeClosed(1, ServiceUtil.getNumbersOfPages(pageSize, countUsers))
                .boxed()
                .collect(Collectors.toList());
        page.getNumbersOfPage().addAll(numbersOfPages);
        return page;
    }

    @Override
    @Transactional
    public void add(UserDTO userDTO, Long roleId) {
        User userByEmail = userRepository.getUserByEmail(userDTO.getEmail());
        if (Objects.isNull(userByEmail)) {
            User user = getUserWithPasswordAndUuid(userDTO);
            Role role = roleRepository.findById(roleId);
            if (Objects.nonNull(role)) {
                user.setRole(role);
            } else {
                throw new ServiceException("Role with id '" + roleId + "' does not exist");
            }
            userRepository.persist(user);
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
            throw new ServiceException("User with id '" + id + "' does not exist");
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
            throw new ServiceException("User with id '" + id + "' does not exist");
        }
    }

    @Override
    @Transactional
    public void changeRoleById(Long userId, Long roleId) {
        User user = userRepository.findById(userId);
        if (Objects.nonNull(user)) {
            Role role = roleRepository.findById(roleId);
            user.setRole(role);
        } else {
            throw new ServiceException("User with id '" + userId + "' does not exist");
        }
    }

    @Override
    @Transactional
    public void addAndSendEmail(UserDTO user, Long roleId) {
        String password = RandomString.make(PASSWORD_LENGTH);
        String encodedPassword = passwordEncoder.encode(password);
        user.setPassword(encodedPassword);
        add(user, roleId);
        mailSender.send(user.getEmail(), password);
    }

    @Override
    @Transactional
    public void addAndSendEmail(UserDTO user) {
        String password = RandomString.make(PASSWORD_LENGTH);
        String encodedPassword = passwordEncoder.encode(password);
        user.setPassword(encodedPassword);
        add(user);
        mailSender.send(user.getEmail(), password);
    }

    @Override
    @Transactional
    public void add(UserDTO userDTO) {
        User userByEmail = userRepository.getUserByEmail(userDTO.getEmail());
        if (Objects.isNull(userByEmail)) {
            User user = getUserWithPasswordAndUuid(userDTO);
            String roleName = userDTO.getRoleName();
            try {
                RolesEnum roleEnum = RolesEnum.valueOf(roleName);
                Role role = roleRepository.findByName(roleEnum);
                if (Objects.nonNull(role)) {
                    user.setRole(role);
                } else {
                    throw new ServiceException("Role with name '" + userDTO.getRoleName() + "' does not exist");
                }
                userRepository.persist(user);
            } catch (IllegalArgumentException e) {
                log.error(e.getMessage(), e);
                throw new ServiceException("Role with name '" + userDTO.getRoleName() + "' does not exist");
            }
        } else {
            throw new ServiceException("User with email: " + userDTO.getEmail() + " already exist");
        }
    }

    private User getUserWithPasswordAndUuid(UserDTO userDTO) {
        User user = userConverter.convert(userDTO);
        UUID uuid = UUID.randomUUID();
        user.setUniqueNumber(uuid);
        if (Objects.isNull(userDTO.getPassword())) {
            String password = RandomString.make(PASSWORD_LENGTH);
            String encodedPassword = passwordEncoder.encode(password);
            user.setPassword(encodedPassword);
        }
        return user;
    }
}
