package com.hanna.sapeha.app.config.handler;

import com.hanna.sapeha.app.controller.exception.ControllerException;
import com.hanna.sapeha.app.repository.model.enums.RolesEnum;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static com.hanna.sapeha.app.constant.HandlerConstants.ARTICLES_URL;
import static com.hanna.sapeha.app.constant.HandlerConstants.USERS_URL;

public class AppSuccessHandler implements AuthenticationSuccessHandler {
    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    private final Map<String, String> roleTargetUrlMap = new HashMap<>();

    public AppSuccessHandler() {
        roleTargetUrlMap.put(RolesEnum.ADMINISTRATOR.name(), USERS_URL);
        roleTargetUrlMap.put(RolesEnum.CUSTOMER_USER.name(), ARTICLES_URL);
        roleTargetUrlMap.put(RolesEnum.SALE_USER.name(), ARTICLES_URL);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        String targetUrl = determineTargetUrl(authentication);
        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    private String determineTargetUrl(Authentication authentication) {
        final Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority authority : authorities) {
            String authorityName = authority.getAuthority();
            if (roleTargetUrlMap.containsKey(authorityName)) {
                return roleTargetUrlMap.get(authorityName);
            }
        }
        throw new ControllerException("Role does not exist");
    }
}
