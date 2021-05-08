package com.hanna.sapeha.app.config.handler;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.hanna.sapeha.app.constant.HandlerConstants.ACCESS_DENIED_URL;

@Log4j2
public class AppAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            log.info("User '{}' attempted to access the protected URL: {}", auth.getName(), request.getRequestURI());
        }
        response.sendRedirect(request.getContextPath() + ACCESS_DENIED_URL);
    }
}
