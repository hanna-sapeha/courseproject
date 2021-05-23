package com.hanna.sapeha.app.service.converter;

import com.hanna.sapeha.app.repository.model.Article;
import com.hanna.sapeha.app.repository.model.Comment;
import com.hanna.sapeha.app.repository.model.User;
import com.hanna.sapeha.app.service.model.ArticleDTO;
import com.hanna.sapeha.app.service.model.ArticleFormDTO;
import com.hanna.sapeha.app.service.model.CommentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ArticleConverter implements GeneralConverter<Article, ArticleDTO> {
    private final GeneralConverter<Comment, CommentDTO> converter;

    @Override
    public ArticleDTO convertEntityToDTO(Article article) {
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setId(article.getId());
        articleDTO.setUuid(article.getUuid());
        articleDTO.setDateAdded(article.getDateAdded());
        articleDTO.setTitle(article.getTitle());
        articleDTO.setContent(article.getContent());
        User user = article.getUser();
        if (Objects.nonNull(user)) {
            articleDTO.setUserFirstname(user.getFirstname());
            articleDTO.setUserLastname(user.getLastname());
        }
        Set<Comment> comments = article.getComments();
        if (Objects.nonNull(comments)) {
            List<CommentDTO> commentDTOs = converter.convertEntitiesToDTO(List.copyOf(comments));
            articleDTO.setComments(commentDTOs);
        }
        return articleDTO;
    }

    @Override
    public List<ArticleDTO> convertEntitiesToDTO(List<Article> articles) {
        return articles.stream()
                .map(this::convertEntityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Article convertDTOToEntity(ArticleDTO articleDTO) {
        Article article = new Article();
        article.setTitle(articleDTO.getTitle());
        article.setContent(articleDTO.getContent());
        article.setDateAdded(articleDTO.getDateAdded());
        return article;
    }

    public Article convertDTOToEntity(ArticleFormDTO articleDTO) {
        Article article = new Article();
        article.setTitle(articleDTO.getTitle());
        article.setContent(articleDTO.getContent());
        article.setDateAdded(articleDTO.getDateAdded());
        return article;
    }
}
