package com.hanna.sapeha.app.service.impl;

import com.hanna.sapeha.app.repository.ArticleRepository;
import com.hanna.sapeha.app.repository.model.Article;
import com.hanna.sapeha.app.service.converter.ArticleConverter;
import com.hanna.sapeha.app.service.model.ArticleDTO;
import com.hanna.sapeha.app.service.model.PageDTO;
import com.hanna.sapeha.app.service.util.ServiceUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ArticleServiceImplTest {
    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private ArticleConverter articleConverter;

    @InjectMocks
    private ArticleServiceImpl articleService;

    @Test
    void shouldGetArticlesForPageNumber() {
        int pageNumber = 1;
        int pageSize = 10;

        Article article = new Article();
        article.setId(1L);
        List<Article> articles = Collections.singletonList(article);
        when(articleRepository.findAll(pageNumber, pageSize)).thenReturn(articles);

        List<ArticleDTO> articleDTOS = articleConverter.convertEntitiesToDTO(articles);

        PageDTO<ArticleDTO> pageDTO = new PageDTO<>();
        pageDTO.getObjects().addAll(articleDTOS);

        when(articleRepository.getCount()).thenReturn(Long.valueOf(articleDTOS.size()));
        Long countNews = articleRepository.getCount();

        List<Integer> numbersOfPages = IntStream.rangeClosed(1, ServiceUtil.getNumbersOfPages(pageSize, countNews))
                .boxed()
                .collect(Collectors.toList());
        pageDTO.setNumbersOfPage(numbersOfPages);

        PageDTO<ArticleDTO> articlesForPage = articleService.getAllByPagination(pageNumber, pageSize);

        Assertions.assertEquals(pageDTO.getObjects(), articlesForPage.getObjects());
    }

    @Test
    void shouldGetAllArticles() {
        Article article = new Article();
        long id = 1L;
        article.setId(id);
        List<Article> articles = Collections.singletonList(article);
        when(articleRepository.findAll()).thenReturn(articles);

        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setId(id);
        List<ArticleDTO> articleDTOs = Collections.singletonList(articleDTO);
        when(articleConverter.convertEntitiesToDTO(articles)).thenReturn(articleDTOs);

        List<ArticleDTO> result = articleService.getArticles();
        assertEquals(id, result.get(0).getId());

    }

    @Test
    void shouldGetArticleById() {
        Article article = new Article();
        long id = 1L;
        article.setId(id);
        when(articleRepository.findById(id)).thenReturn(article);

        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setId(id);
        when(articleConverter.convertEntityToDTO(article)).thenReturn(articleDTO);

        ArticleDTO result = articleService.getArticle(id);
        assertEquals(id, result.getId());
    }
}