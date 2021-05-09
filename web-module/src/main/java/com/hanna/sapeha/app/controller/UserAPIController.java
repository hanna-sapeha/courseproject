package com.hanna.sapeha.app.controller;

import com.hanna.sapeha.app.service.UserService;
import com.hanna.sapeha.app.service.model.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Log4j2
public class UserAPIController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<Void> addUser(@RequestBody @Valid UserDTO user,
                                        BindingResult errors) {
        if (!errors.hasErrors()) {
            userService.add(user);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            log.error("User filling error: {}", errors);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
