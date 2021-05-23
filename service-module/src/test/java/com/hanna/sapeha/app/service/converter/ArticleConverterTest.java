package com.hanna.sapeha.app.service.converter;

import com.hanna.sapeha.app.repository.model.Article;
import com.hanna.sapeha.app.repository.model.Comment;
import com.hanna.sapeha.app.repository.model.User;
import com.hanna.sapeha.app.service.model.ArticleDTO;
import com.hanna.sapeha.app.service.model.CommentDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
class ArticleConverterTest {

    @Mock
    private GeneralConverter<Comment, CommentDTO> converter;

    @InjectMocks
    private ArticleConverter articleConverter;

    @Test
    void shouldConvertDtoToArticleAndReturnRightTitle() {
        ArticleDTO articleDTO = new ArticleDTO();
        String title = "test title";
        articleDTO.setTitle(title);
        Article article = articleConverter.convertDTOToEntity(articleDTO);
        assertEquals(title, article.getTitle());
    }

    @Test
    void shouldConvertDtoToArticleAndReturnRightContent() {
        ArticleDTO articleDTO = new ArticleDTO();
        String content = "test content";
        articleDTO.setContent(content);
        Article article = articleConverter.convertDTOToEntity(articleDTO);
        assertEquals(content, article.getContent());
    }

    @Test
    void shouldConvertDtoToArticleAndReturnNullFilds() {
        ArticleDTO articleDTO = new ArticleDTO();
        long id = 1L;
        articleDTO.setId(id);
        UUID uuid = UUID.randomUUID();
        articleDTO.setUuid(uuid);
        LocalDate date = LocalDate.now();
        articleDTO.setDateAdded(date);

        Article article = articleConverter.convertDTOToEntity(articleDTO);
        assertNull(article.getId());
        assertNull(article.getUuid());
    }

    @Test
    void shouldConvertArticleToDTOAndReturnRightId() {
        Article article = new Article();
        long id = 1L;
        article.setId(id);
        ArticleDTO articleDTO = articleConverter.convertEntityToDTO(article);
        assertEquals(id, articleDTO.getId());
    }

    @Test
    void shouldConvertArticleToDTOAndReturnRightUUID() {
        Article article = new Article();
        UUID uuid = UUID.randomUUID();
        article.setUuid(uuid);
        ArticleDTO articleDTO = articleConverter.convertEntityToDTO(article);
        assertEquals(uuid, articleDTO.getUuid());
    }

    @Test
    void shouldConvertArticleToDTOAndReturnRightDateAdded() {
        Article article = new Article();
        LocalDate date = LocalDate.now();
        article.setDateAdded(date);
        ArticleDTO articleDTO = articleConverter.convertEntityToDTO(article);
        assertEquals(date, articleDTO.getDateAdded());
    }

    @Test
    void shouldConvertArticleToDTOAndReturnRightTitle() {
        Article article = new Article();
        String title = "test title";
        article.setTitle(title);
        ArticleDTO articleDTO = articleConverter.convertEntityToDTO(article);
        assertEquals(title, articleDTO.getTitle());
    }

    @Test
    void shouldConvertArticleToDTOAndReturnRightContent() {
        Article article = new Article();
        String content = "test content";
        article.setContent(content);
        ArticleDTO articleDTO = articleConverter.convertEntityToDTO(article);
        assertEquals(content, articleDTO.getContent());
    }

    @Test
    void shouldConvertArticleToDTOAndReturnRightUserFirstname() {
        User user = new User();
        String firstname = "test firstname";
        user.setFirstname(firstname);
        Article article = new Article();
        article.setUser(user);

        ArticleDTO articleDTO = articleConverter.convertEntityToDTO(article);
        assertEquals(firstname, articleDTO.getUserFirstname());
    }

    @Test
    void shouldConvertArticleToDTOAndReturnRightUserLastname() {
        User user = new User();
        String lastname = "test lastname";
        user.setLastname(lastname);
        Article article = new Article();
        article.setUser(user);

        ArticleDTO articleDTO = articleConverter.convertEntityToDTO(article);
        assertEquals(lastname, articleDTO.getUserLastname());
    }
}