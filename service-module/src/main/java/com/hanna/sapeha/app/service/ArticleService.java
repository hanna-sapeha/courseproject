package com.hanna.sapeha.app.service;

import com.hanna.sapeha.app.service.model.ArticleDTO;
import com.hanna.sapeha.app.service.model.ArticleFormDTO;
import com.hanna.sapeha.app.service.model.PageDTO;

import java.util.List;
import java.util.UUID;

public interface ArticleService {

    PageDTO<ArticleDTO> getAllByPagination(int pageNumber, int pageSize);

    ArticleDTO getArticle(Long id);

    ArticleDTO getArticleByUuid(UUID uuid);

    void removeById(Long id);

    List<ArticleDTO> getArticles();

    ArticleDTO add(ArticleFormDTO article, String authorizedUserEmail);

    ArticleDTO changeArticle(UUID articleUuid, ArticleFormDTO articleForm);
}
