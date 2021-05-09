package com.hanna.sapeha.app.controller;

import com.hanna.sapeha.app.service.UserProfileService;
import com.hanna.sapeha.app.service.UserService;
import com.hanna.sapeha.app.service.model.UserLogin;
import com.hanna.sapeha.app.service.model.UserProfileDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

import static com.hanna.sapeha.app.constant.HandlerConstants.PROFILE_URL;

@Controller
@RequestMapping(PROFILE_URL)
@RequiredArgsConstructor
@Log4j2
public class ProfileController {
    private final UserService userService;
    private final UserProfileService userProfileService;

    @GetMapping
    public String getProfile(Model model) {
        UserLogin user = getAuthorizedUser();
        UserProfileDTO userInfo = userProfileService.getAllInfoUser(user.getUsername());
        model.addAttribute("user", userInfo);
        return "profile";
    }

    @GetMapping("/update-user")
    public String redirectToAddItem(UserProfileDTO user) {
        return "update-user";
    }

    @PostMapping("/update-user")
    public String changeFields(@Valid UserProfileDTO user, BindingResult errors) {
        if (errors.hasErrors()) {
            log.error("User filling error: {}", errors);
            return "update-user";
        } else {
            UserLogin authorizedUser = getAuthorizedUser();
            userProfileService.updateUserByParameters(user, authorizedUser.getUsername());
            return "redirect:" + PROFILE_URL;
        }
    }

    @PostMapping("/change-password")
    public String changePassword(@RequestParam(value = "userIdForChangePass", required = false) Long id) {
        userService.changePasswordById(id);
        return "redirect:" + PROFILE_URL;
    }

    private UserLogin getAuthorizedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (UserLogin) auth.getPrincipal();
    }
}
