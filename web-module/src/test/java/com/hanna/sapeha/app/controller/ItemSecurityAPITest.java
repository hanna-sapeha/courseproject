package com.hanna.sapeha.app.controller;

import com.hanna.sapeha.app.config.UserDetailsServiceConfig;
import com.hanna.sapeha.app.service.ItemService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(excludeAutoConfiguration = UserDetailsServiceAutoConfiguration.class,
        controllers = ItemAPIController.class)
@Import(UserDetailsServiceConfig.class)
class ItemSecurityAPITest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemService itemService;

    @Test
    void shouldUserWithRoleSecureRestApiHasAccessToGetItems() throws Exception {
        mockMvc.perform(
                get(API_URL + ITEMS_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("user", "1234"))
        ).andExpect(status().isOk());
    }

    @Test
    void shouldUserWithWithWrongPassTryToGetAccessToGetItems() throws Exception {
        mockMvc.perform(
                get(API_URL + ITEMS_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("user", "1245"))
        ).andExpect(status().isUnauthorized());
    }

    @Test
    void shouldUserWithWithWrongRoleTryToGetAccessToGetItems() throws Exception {
        mockMvc.perform(
                get(API_URL + ITEMS_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("admin", "1234"))
        ).andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = {"CUSTOMER_USER"})
    void shouldUserWithWithCustomerRoleTryToGetAccessToGetUsers() throws Exception {
        mockMvc.perform(
                get(API_URL + ITEMS_URL)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = {"SALE_USER"})
    void shouldUserWithWithSaleUserRoleTryToGetAccessToGetUsers() throws Exception {
        mockMvc.perform(
                get(API_URL + ITEMS_URL)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = {"ADMINISTRATOR"})
    void shouldUserWithWithAdminRoleTryToGetAccessToGetUsers() throws Exception {
        mockMvc.perform(
                get(API_URL + ITEMS_URL)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isForbidden());
    }
}