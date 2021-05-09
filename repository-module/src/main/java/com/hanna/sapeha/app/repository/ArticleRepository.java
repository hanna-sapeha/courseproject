package com.hanna.sapeha.app.repository;

import com.hanna.sapeha.app.repository.model.Article;

import java.util.List;
import java.util.UUID;

public interface ArticleRepository extends GenericRepository<Long, Article> {

    List<Article> findAll(int pageNumber, int pageSize);

    Article findByUuid(UUID uuid);
}
