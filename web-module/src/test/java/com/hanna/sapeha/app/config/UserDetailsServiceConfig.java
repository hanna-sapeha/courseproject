package com.hanna.sapeha.app.config;

import com.hanna.sapeha.app.repository.model.enums.RolesEnum;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.Collections;

@Configuration
public class UserDetailsServiceConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                switch (username) {
                    case "user":
                        return getUserDetails();
                    case "admin":
                        return getAdminDetails();
                    default:
                        throw new UsernameNotFoundException(MessageFormat.format("User with %s was not found", username));

                }
            }

            private UserDetails getAdminDetails() {
                return new UserDetails() {
                    @Override
                    public Collection<? extends GrantedAuthority> getAuthorities() {
                        String role = RolesEnum.ADMINISTRATOR.name();
                        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role);
                        return Collections.singletonList(grantedAuthority);
                    }

                    @Override
                    public String getPassword() {
                        return new BCryptPasswordEncoder().encode("1234");
                    }

                    @Override
                    public String getUsername() {
                        return "admin";
                    }

                    @Override
                    public boolean isAccountNonExpired() {
                        return true;
                    }

                    @Override
                    public boolean isAccountNonLocked() {
                        return true;
                    }

                    @Override
                    public boolean isCredentialsNonExpired() {
                        return true;
                    }

                    @Override
                    public boolean isEnabled() {
                        return true;
                    }
                };
            }

            private UserDetails getUserDetails() {
                return new UserDetails() {
                    @Override
                    public Collection<? extends GrantedAuthority> getAuthorities() {
                        String role = RolesEnum.SECURE_REST_API.name();
                        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role);
                        return Collections.singletonList(grantedAuthority);
                    }

                    @Override
                    public String getPassword() {
                        return new BCryptPasswordEncoder().encode("1234");
                    }

                    @Override
                    public String getUsername() {
                        return "user";
                    }

                    @Override
                    public boolean isAccountNonExpired() {
                        return true;
                    }

                    @Override
                    public boolean isAccountNonLocked() {
                        return true;
                    }

                    @Override
                    public boolean isCredentialsNonExpired() {
                        return true;
                    }

                    @Override
                    public boolean isEnabled() {
                        return true;
                    }
                };
            }
        };
    }
}
