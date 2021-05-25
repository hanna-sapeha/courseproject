package com.hanna.sapeha.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanna.sapeha.app.controller.model.ErrorDTO;
import com.hanna.sapeha.app.service.ArticleService;
import com.hanna.sapeha.app.service.model.ArticleDTO;
import com.hanna.sapeha.app.service.model.ArticleFormDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static com.hanna.sapeha.app.constant.HandlerConstants.API_URL;
import static com.hanna.sapeha.app.constant.HandlerConstants.ARTICLES_URL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ArticleAPIController.class,
        excludeAutoConfiguration = UserDetailsServiceAutoConfiguration.class)
@ActiveProfiles("test")
class ArticleAPIControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ArticleService articleService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldGetArticlesAndExpectOk() throws Exception {
        mockMvc.perform(
                get(API_URL + ARTICLES_URL)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    void shouldGetArticlesAndReturnEmptyList() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                get(API_URL + ARTICLES_URL)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        assertThat(contentAsString).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(Collections.emptyList()));
    }

    @Test
    void shouldReturnCollectionWhenGetArticles() throws Exception {
        ArticleDTO showArticleDTO = new ArticleDTO();
        showArticleDTO.setId(1L);
        showArticleDTO.setDateAdded(LocalDate.now());

        List<ArticleDTO> articleDTOS = Collections.singletonList(showArticleDTO);

        when(articleService.getArticles()).thenReturn(articleDTOS);

        MvcResult mvcResult = mockMvc.perform(
                get(API_URL + ARTICLES_URL)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        assertThat(content).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(articleDTOS));
    }

    @Test
    void shouldNoCreateEmptyArticle() throws Exception {
        ArticleFormDTO articleForm = new ArticleFormDTO();
        mockMvc.perform(
                post(API_URL + ARTICLES_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(articleForm))
        ).andExpect(status().isBadRequest());
    }

    @Test
    void shouldGetArticleByIdAndReturnStatusOk() throws Exception {
        ArticleDTO articleDTO = new ArticleDTO();
        long id = 1L;
        articleDTO.setId(id);
        articleDTO.setTitle("title");

        when(articleService.getArticle(id)).thenReturn(articleDTO);

        MvcResult mvcResult = mockMvc.perform(
                get(API_URL + ARTICLES_URL + "/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        assertThat(content).isEqualToIgnoringCase(objectMapper.writeValueAsString(articleDTO));
    }

    @Test
    void shouldAddNotValidTitleArticle() throws Exception {
        ArticleFormDTO articleForm = new ArticleFormDTO();
        articleForm.setTitle("a");
        articleForm.setContent("content");
        articleForm.setDateAdded(LocalDate.now());

        MvcResult result = mockMvc.perform(
                post(API_URL + ARTICLES_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(articleForm))
        ).andExpect(status().isBadRequest()).andReturn();

        ErrorDTO error = new ErrorDTO();
        String errorMessage = "characters count should be in the range between 2 and 200";
        error.add(errorMessage);

        String content = result.getResponse().getContentAsString();
        assertThat(content).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(error));
    }

    @Test
    void shouldAddNotValidContentArticle() throws Exception {
        ArticleFormDTO articleForm = new ArticleFormDTO();
        articleForm.setTitle("article");
        articleForm.setContent("a");
        articleForm.setDateAdded(LocalDate.now());

        MvcResult result = mockMvc.perform(
                post(API_URL + ARTICLES_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(articleForm))
        ).andExpect(status().isBadRequest()).andReturn();

        ErrorDTO error = new ErrorDTO();
        String errorMessage = "characters count should be in the range between 2 and 1000";
        error.add(errorMessage);

        String content = result.getResponse().getContentAsString();
        assertThat(content).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(error));
    }

    @Test
    void shouldAddNotValidDateArticle() throws Exception {
        ArticleFormDTO articleForm = new ArticleFormDTO();
        articleForm.setTitle("article");
        articleForm.setContent("content");
        articleForm.setDateAdded(null);

        MvcResult result = mockMvc.perform(
                post(API_URL + ARTICLES_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(articleForm))
        ).andExpect(status().isBadRequest()).andReturn();

        ErrorDTO error = new ErrorDTO();
        String errorMessage = "must not be null";
        error.add(errorMessage);

        String content = result.getResponse().getContentAsString();
        assertThat(content).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(error));
    }
}