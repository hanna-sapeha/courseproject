package com.hanna.sapeha.app.service.impl;

import com.hanna.sapeha.app.repository.UserRepository;
import com.hanna.sapeha.app.repository.model.User;
import com.hanna.sapeha.app.repository.model.UserDetails;
import com.hanna.sapeha.app.service.UserProfileService;
import com.hanna.sapeha.app.service.converter.UserProfileConverter;
import com.hanna.sapeha.app.service.model.UserProfileDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {
    private final UserRepository userRepository;
    private final UserProfileConverter userProfileConverter;

    @Override
    @Transactional
    public UserProfileDTO updateUserByParameters(UserProfileDTO userForm, String email) {
        User user = userRepository.getUserByEmail(email);
        if (Objects.nonNull(user)) {
            if (!userForm.getFirstname().isBlank()) {
                user.setFirstname(userForm.getFirstname());
            }
            if (!userForm.getLastname().isBlank()) {
                user.setLastname(userForm.getLastname());
            }
            UserDetails userDetails = user.getUserDetails();
            if (Objects.isNull(userDetails)) {
                userDetails = new UserDetails();
                user.setUserDetails(userDetails);
                userDetails.setUser(user);
            }
            if (!userForm.getAddress().isBlank()) {
                userDetails.setAddress(userForm.getAddress());
            }
            if (!userForm.getTelephone().isBlank()) {
                userDetails.setTelephone(userForm.getTelephone());
            }
        }
        return userProfileConverter.convert(user);
    }

    @Override
    @Transactional
    public UserProfileDTO getAllInfoUser(String email) {
        User user = userRepository.getUserByEmail(email);
        return userProfileConverter.convert(user);
    }
}
