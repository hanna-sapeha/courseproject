package com.hanna.sapeha.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanna.sapeha.app.config.UserDetailsServiceConfigTest;
import com.hanna.sapeha.app.service.UserService;
import com.hanna.sapeha.app.service.model.UserDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import static com.hanna.sapeha.app.constant.HandlerConstants.API_URL;
import static com.hanna.sapeha.app.constant.HandlerConstants.ITEMS_URL;
import static com.hanna.sapeha.app.constant.HandlerConstants.USERS_URL;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(excludeAutoConfiguration = UserDetailsServiceAutoConfiguration.class,
        controllers = UserAPIController.class)
@Import(UserDetailsServiceConfigTest.class)
class UsersSecurityAPITest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldUserWithRoleSecureRestApiHasAccessToAddUser() throws Exception {
        UserDTO user = getValidUser();
        mockMvc.perform(
                post(API_URL + USERS_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user))
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("user", "1234"))
        ).andExpect(status().isCreated());
    }

    @Test
    void shouldUserWithWithWrongPassTryToGetAccessToAddUser() throws Exception {
        UserDTO user = getValidUser();
        mockMvc.perform(
                post(API_URL + ITEMS_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user))
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("user", "1245"))
        ).andExpect(status().isUnauthorized());
    }

    @Test
    void shouldUserWithWithWrongRoleTryToGetAccessToAddUser() throws Exception {
        UserDTO user = getValidUser();
        mockMvc.perform(
                get(API_URL + ITEMS_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user))
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("admin", "1234"))
        ).andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = {"CUSTOMER_USER"})
    void shouldUserWithWithCustomerRoleTryToGetAccessToAddUser() throws Exception {
        UserDTO user = getValidUser();
        mockMvc.perform(
                get(API_URL + ITEMS_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user))
        ).andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = {"SALE_USER"})
    void shouldUserWithWithSaleUserRoleTryToGetAccessToAddUser() throws Exception {
        UserDTO user = getValidUser();
        mockMvc.perform(
                get(API_URL + ITEMS_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user))
        ).andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = {"ADMINISTRATOR"})
    void shouldUserWithWithAdminRoleTryToGetAccessToAddUser() throws Exception {
        UserDTO user = getValidUser();
        mockMvc.perform(
                get(API_URL + ITEMS_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user))
        ).andExpect(status().isForbidden());
    }

    private UserDTO getValidUser() {
        UserDTO user = new UserDTO();
        user.setFirstname("test firstname");
        user.setLastname("test Lastname");
        user.setPatronymic("test Patronymic");
        user.setEmail("sdfsd@sdf.ds");
        return user;
    }
}