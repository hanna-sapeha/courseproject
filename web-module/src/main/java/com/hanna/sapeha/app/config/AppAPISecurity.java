package com.hanna.sapeha.app.config;

import com.hanna.sapeha.app.repository.model.enums.RolesEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.hanna.sapeha.app.constant.HandlerConstants.API_URL;

@Configuration
@Order(2)
@RequiredArgsConstructor
public class AppAPISecurity extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(API_URL + "/**")
                .hasAuthority(RolesEnum.SECURE_REST_API.name())
                .and()
                .httpBasic()
                .and()
                .csrf()
                .disable();
    }
}
