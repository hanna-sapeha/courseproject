package com.hanna.sapeha.app.config;

import com.hanna.sapeha.app.config.handler.AppAccessDeniedHandler;
import com.hanna.sapeha.app.config.handler.AppSuccessHandler;
import com.hanna.sapeha.app.repository.model.enums.RolesEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.hanna.sapeha.app.constant.HandlerConstants.API_URL;
import static com.hanna.sapeha.app.constant.HandlerConstants.ARTICLES_URL;
import static com.hanna.sapeha.app.constant.HandlerConstants.ITEMS_URL;
import static com.hanna.sapeha.app.constant.HandlerConstants.LOGIN_URL;
import static com.hanna.sapeha.app.constant.HandlerConstants.ORDERS_URL;
import static com.hanna.sapeha.app.constant.HandlerConstants.PROFILE_URL;
import static com.hanna.sapeha.app.constant.HandlerConstants.REVIEWS_URL;
import static com.hanna.sapeha.app.constant.HandlerConstants.USERS_URL;

@Configuration
public class AppSecurityConfig {

    @Configuration
    @Order(1)
    @RequiredArgsConstructor
    @Profile("!test")
    public static class AppAPISecurity extends WebSecurityConfigurerAdapter {
        private final UserDetailsService userDetailsService;

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userDetailsService)
                    .passwordEncoder(encoder());
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.antMatcher(API_URL + "/**")
                    .authorizeRequests()
                    .anyRequest()
                    .hasAuthority(RolesEnum.SECURE_REST_API.name())
                    .and()
                    .httpBasic()
                    .and()
                    .csrf()
                    .disable();
        }
    }

    @Configuration
    @RequiredArgsConstructor
    @Profile("!test")
    public static class AppWebSecurity extends WebSecurityConfigurerAdapter {
        private final UserDetailsService userDetailsService;

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userDetailsService)
                    .passwordEncoder(encoder());
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests()
                    .antMatchers(USERS_URL + "/add")
                    .permitAll()
                    .antMatchers(USERS_URL + "/**", REVIEWS_URL, REVIEWS_URL + "/delete", REVIEWS_URL + "/change-status")
                    .hasAuthority(RolesEnum.ADMINISTRATOR.name())
                    .antMatchers(ARTICLES_URL + "/**", ITEMS_URL + "/**", ORDERS_URL + "/**")
                    .hasAnyAuthority(RolesEnum.CUSTOMER_USER.name(), RolesEnum.SALE_USER.name())
                    .antMatchers(REVIEWS_URL + "/add", PROFILE_URL + "/**")
                    .hasAuthority(RolesEnum.CUSTOMER_USER.name())
                    .and()
                    .formLogin()
                    .usernameParameter("email")
                    .loginPage(LOGIN_URL)
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

    @Bean
    public static PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}

