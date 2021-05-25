package com.hanna.sapeha.app.controller;

import com.hanna.sapeha.app.config.UserDetailsServiceConfig;
import com.hanna.sapeha.app.service.ArticleService;
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
import static com.hanna.sapeha.app.constant.HandlerConstants.ARTICLES_URL;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(excludeAutoConfiguration = UserDetailsServiceAutoConfiguration.class,
        controllers = ArticleAPIController.class)
@Import(UserDetailsServiceConfig.class)
class ArticleSecurityAPITest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ArticleService articleService;

    @Test
    void shouldUserWithRoleSecureRestApiHasAccessToGetArticles() throws Exception {
        mockMvc.perform(
                get(API_URL + ARTICLES_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("user", "1234"))
        ).andExpect(status().isOk());
    }

    @Test
    void shouldUserWithWithWrongPassTryToGetAccessToGetArticles() throws Exception {
        mockMvc.perform(
                get(API_URL + ARTICLES_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("user", "1245"))
        ).andExpect(status().isUnauthorized());
    }

    @Test
    void shouldUserWithWithWrongRoleTryToGetAccessToGetArticles() throws Exception {
        mockMvc.perform(
                get(API_URL + ARTICLES_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("admin", "1234"))
        ).andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = {"CUSTOMER_USER"})
    void shouldUserWithWithCustomerRoleTryToGetAccessToGetArticles() throws Exception {
        mockMvc.perform(
                get(API_URL + ARTICLES_URL)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = {"SALE_USER"})
    void shouldUserWithWithSaleUserRoleTryToGetAccessToGetArticles() throws Exception {
        mockMvc.perform(
                get(API_URL + ARTICLES_URL)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = {"ADMINISTRATOR"})
    void shouldUserWithWithAdminRoleTryToGetAccessToGetArticles() throws Exception {
        mockMvc.perform(
                get(API_URL + ARTICLES_URL)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isForbidden());
    }
}