package com.hanna.sapeha.app.controller;

import com.hanna.sapeha.app.service.RoleService;
import com.hanna.sapeha.app.service.UserService;
import com.hanna.sapeha.app.service.model.PageDTO;
import com.hanna.sapeha.app.service.model.RoleDTO;
import com.hanna.sapeha.app.service.model.UserDTO;
import com.hanna.sapeha.app.service.model.UserLogin;
import com.hanna.sapeha.app.util.ControllerUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
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

import static com.hanna.sapeha.app.constant.HandlerConstants.USERS_URL;

@Controller
@RequestMapping(USERS_URL)
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
                          BindingResult errors,
                          @RequestParam(value = "roleId", required = false) Long roleId) {
        if (errors.hasErrors()) {
            return "register-user";
        } else {
            userService.addAndSendEmail(user, roleId);
            return "redirect:" + USERS_URL;
        }
    }

    @GetMapping
    public String getAllUsers(Model model,
                              @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                              @RequestParam(value = "page", required = false, defaultValue = "1") int pageNumber) {
        PageDTO<UserDTO> page = userService.getAllByPagination(pageNumber, pageSize);
        model.addAttribute("page", page);
        List<RoleDTO> roles = roleService.getAll();
        model.addAttribute("roles", roles);

        UserLogin authorizedUser = ControllerUtil.getAuthorizedUser();
        model.addAttribute("authUserId", authorizedUser.getId());
        return "users";
    }

    @PostMapping("/delete")
    public String deleteItems(@RequestParam(value = "selected", required = false) Long[] ids) {
        if (Objects.nonNull(ids)) {
            for (Long id : ids) {
                userService.removeById(id);
            }
        }
        return "redirect:" + USERS_URL;
    }

    @PostMapping("/change-password")
    public String changePassword(@RequestParam(value = "userIdForChangePass", required = false) Long id) {
        userService.changePasswordById(id);
        return "redirect:" + USERS_URL;
    }

    @PostMapping("/change-role")
    public String changeRole(@RequestParam(value = "id") Long idUser,
                             @RequestParam(value = "roleId") Long roleId) {
        userService.changeRoleById(idUser, roleId);
        return "redirect:" + USERS_URL;
    }
}
