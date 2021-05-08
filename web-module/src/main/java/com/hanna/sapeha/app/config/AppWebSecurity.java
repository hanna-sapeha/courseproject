package com.hanna.sapeha.app.config;

import com.hanna.sapeha.app.config.handler.AppAccessDeniedHandler;
import com.hanna.sapeha.app.config.handler.AppSuccessHandler;
import com.hanna.sapeha.app.repository.model.enums.RolesEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.hanna.sapeha.app.constant.HandlerConstants.REVIEWS_URL;
import static com.hanna.sapeha.app.constant.HandlerConstants.USERS_URL;

@Configuration
@RequiredArgsConstructor
public class AppWebSecurity extends WebSecurityConfigurerAdapter {
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
                .antMatchers(USERS_URL + "/add")
                .permitAll()
                .antMatchers(USERS_URL + "/**", REVIEWS_URL + "/**")
                .hasAuthority(RolesEnum.ADMINISTRATOR.name())
                .and()
                .formLogin()
                .usernameParameter("email")
                .loginPage("/login")
                .permitAll()
                .successHandler(new AppSuccessHandler())
                .and()
                .logout()
                .permitAll()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(new AppAccessDeniedHandler())
                .and()
                .csrf()
                .disable();
    }
}
