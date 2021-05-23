package com.hanna.sapeha.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanna.sapeha.app.controller.model.ErrorDTO;
import com.hanna.sapeha.app.service.UserService;
import com.hanna.sapeha.app.service.model.UserDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static com.hanna.sapeha.app.constant.HandlerConstants.API_URL;
import static com.hanna.sapeha.app.constant.HandlerConstants.USERS_URL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserAPIController.class,
        excludeAutoConfiguration = UserDetailsServiceAutoConfiguration.class)
@ActiveProfiles("test")
class UserAPIControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    void shouldAddValidUser() throws Exception {
        UserDTO user = new UserDTO();
        user.setFirstname("test firstname");
        user.setLastname("test Lastname");
        user.setPatronymic("test Patronymic");
        user.setEmail("sdfsd@sdf.ds");

        mockMvc.perform(
                post(API_URL + USERS_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user))
        ).andExpect(status().isCreated());
    }

    @Test
    void shouldAddNotValidEmailUser() throws Exception {
        UserDTO user = new UserDTO();
        user.setFirstname("test firstname");
        user.setLastname("test Lastname");
        user.setPatronymic("test Patronymic");
        user.setEmail("sdfsdsdf.ds");

        MvcResult result = mockMvc.perform(
                post(API_URL + USERS_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user))
        ).andExpect(status().isBadRequest()).andReturn();

        ErrorDTO error = new ErrorDTO();
        String errorMessage = "invalid e-mail, should match the template 'example@example.ex'";
        error.add(errorMessage);

        String content = result.getResponse().getContentAsString();
        assertThat(content).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(error));
    }

    @Test
    void shouldAddNotValidFirstnameUser() throws Exception {
        UserDTO user = new UserDTO();
        user.setFirstname("");
        user.setLastname("test Lastname");
        user.setPatronymic("test Patronymic");
        user.setEmail("sdfsd@sdf.ds");

        MvcResult result = mockMvc.perform(
                post(API_URL + USERS_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user))
        ).andExpect(status().isBadRequest()).andReturn();

        ErrorDTO error = new ErrorDTO();
        String errorSizeMessage = "characters count should be in the range between 2 and 20";
        error.add(errorSizeMessage);

        String content = result.getResponse().getContentAsString();
        assertThat(content).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(error));
    }

    @Test
    void shouldAddNotValidLastnameUser() throws Exception {
        UserDTO user = new UserDTO();
        user.setFirstname("test firstname");
        user.setLastname("test Lastname");
        user.setPatronymic("");
        user.setEmail("sdfsd@sdf.ds");

        MvcResult result = mockMvc.perform(
                post(API_URL + USERS_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user))
        ).andExpect(status().isBadRequest()).andReturn();

        ErrorDTO error = new ErrorDTO();
        String errorSizeMessage = "characters count should be in the range between 2 and 40";
        error.add(errorSizeMessage);

        String content = result.getResponse().getContentAsString();
        assertThat(content).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(error));
    }

    @Test
    void shouldAddNotValidPatronymicUser() throws Exception {
        UserDTO user = new UserDTO();
        user.setFirstname("test firstname");
        user.setLastname("");
        user.setPatronymic("test Patronymic");
        user.setEmail("sdfsd@sdf.ds");

        MvcResult result = mockMvc.perform(
                post(API_URL + USERS_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user))
        ).andExpect(status().isBadRequest()).andReturn();

        ErrorDTO error = new ErrorDTO();
        String errorSizeMessage = "characters count should be in the range between 2 and 40";
        error.add(errorSizeMessage);

        String content = result.getResponse().getContentAsString();
        assertThat(content).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(error));
    }
}