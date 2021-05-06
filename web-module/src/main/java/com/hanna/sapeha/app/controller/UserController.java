package com.hanna.sapeha.app.controller;

import com.hanna.sapeha.app.service.RoleService;
import com.hanna.sapeha.app.service.UserService;
import com.hanna.sapeha.app.service.model.PageDTO;
import com.hanna.sapeha.app.service.model.RoleDTO;
import com.hanna.sapeha.app.service.model.UserDTO;
import com.hanna.sapeha.app.service.model.UserLogin;
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
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
@Log4j2
public class UserController {
    private final UserService userService;
    private final RoleService roleService;

    @GetMapping("/add")
    public String redirectToAddUser(UserDTO userDTO, Model model) {
        List<RoleDTO> roles = roleService.getAll();
        model.addAttribute("roles", roles);
        return "register-user";
    }

    @PostMapping("/add")
    public String addUser(@Valid UserDTO user,
                          @RequestParam(value = "role", required = false) Long idRole,
                          BindingResult errors) {
        if (errors.hasErrors()) {
            return "register-user";
        } else {
            userService.add(user, idRole);
            return "redirect:/users";
        }
    }

    @GetMapping
    public String getAllUsers(Model model,
                              @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                              @RequestParam(value = "page", required = false, defaultValue = "1") Integer pageNumber) {

        PageDTO<UserDTO> page = userService.getAllByPagination(pageNumber, pageSize);
        model.addAttribute("page", page);
        List<RoleDTO> roles = roleService.getAll();
        model.addAttribute("roles", roles);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserLogin user = (UserLogin) auth.getPrincipal();
        model.addAttribute("authUser", user.getUser());
        log.info(user.getUser().getRole());
        return "users";
    }

    @PostMapping("/delete")
    public String deleteItems(@RequestParam(value = "selected", required = false) Long[] ids) {
        if (Objects.nonNull(ids)) {
            for (Long id : ids) {
                userService.removeById(id);
            }
        }
        return "redirect:/users";
    }

    @PostMapping("/change-password")
    public String changePassword(@RequestParam(value = "userIdForChangePass", required = false) Long id) {
        userService.changePasswordById(id);
        return "redirect:/users";
    }

    @PostMapping("/change-role")
    public String changeRole(@RequestParam(value = "id") Long idUser,
                             @RequestParam(value = "role") Long idRole) {
        userService.changeRoleById(idUser, idRole);
        return "redirect:/users";
    }
}
