package com.hanna.sapeha.app.controller;

import com.hanna.sapeha.app.controller.model.ErrorDTO;
import com.hanna.sapeha.app.service.UserService;
import com.hanna.sapeha.app.service.model.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static com.hanna.sapeha.app.constant.HandlerConstants.API_URL;
import static com.hanna.sapeha.app.constant.HandlerConstants.USERS_URL;

@RestController
@RequestMapping(API_URL + USERS_URL)
@RequiredArgsConstructor
@Log4j2
public class UserAPIController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<ErrorDTO> addUser(@RequestBody @Valid UserDTO user,
                                            BindingResult errors) {
        if (!errors.hasErrors()) {
            userService.addAndSendEmail(user);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            ErrorDTO errorDTO = new ErrorDTO();
            List<String> errorMessages = errors.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            errorDTO.getErrors().addAll(errorMessages);
            log.error("User filling error: {}", errors);
            return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
        }
    }
}
