package com.hanna.sapeha.app.controller;

import com.hanna.sapeha.app.config.BaseIT;
import com.hanna.sapeha.app.service.model.ArticleDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.List;

import static com.hanna.sapeha.app.constant.HandlerConstants.API_URL;
import static com.hanna.sapeha.app.constant.HandlerConstants.ARTICLES_URL;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
class ArticleAPIControllerIT extends BaseIT {

    @Test
    @Sql({"/scripts/clean_article.sql", "/scripts/init_article.sql"})
    void shouldGetAllItems() throws Exception {
        HttpEntity<String> request = new HttpEntity<>(null, new HttpHeaders());
        ResponseEntity<List<ArticleDTO>> response = testRestTemplate.exchange(
                API_URL + ARTICLES_URL,
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<>() {
                }
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals("title", response.getBody().get(0).getTitle());
        assertEquals("content", response.getBody().get(0).getContent());
        assertEquals(LocalDate.now(), response.getBody().get(0).getDateAdded());
        Assertions.assertNotNull(response.getBody().get(0).getUuid());
        Assertions.assertNotNull(response.getBody().get(0).getUserFirstname());
    }
}