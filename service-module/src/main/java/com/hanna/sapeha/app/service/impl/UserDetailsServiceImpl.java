package com.hanna.sapeha.app.service.impl;

import com.hanna.sapeha.app.repository.UserRepository;
import com.hanna.sapeha.app.repository.model.User;
import com.hanna.sapeha.app.service.model.UserLogin;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = userRepository.getUserByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("user with email: " + email + "was not found");
        }
        return new UserLogin(user);
    }
}
