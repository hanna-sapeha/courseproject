package com.hanna.sapeha.app.util;

import com.hanna.sapeha.app.repository.model.enums.RolesEnum;
import com.hanna.sapeha.app.service.model.UserLogin;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class ControllerUtil {

    public static UserLogin getAuthorizedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (UserLogin) auth.getPrincipal();
    }

    public static boolean isAuthorizedAsSaleUser() {
        UserLogin user = getAuthorizedUser();
        return user.getRoleName().equals(RolesEnum.SALE_USER.name());
    }

    public static boolean isAuthorizedAsCustomerUser() {
        UserLogin user = getAuthorizedUser();
        return user.getRoleName().equals(RolesEnum.CUSTOMER_USER.name());
    }
}
