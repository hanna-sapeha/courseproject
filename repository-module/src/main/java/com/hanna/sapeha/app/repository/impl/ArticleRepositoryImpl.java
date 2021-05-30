package com.hanna.sapeha.app.repository.impl;

import com.hanna.sapeha.app.repository.ArticleRepository;
import com.hanna.sapeha.app.repository.exception.RepositoryException;
import com.hanna.sapeha.app.repository.model.Article;
import com.hanna.sapeha.app.repository.model.User;
import com.hanna.sapeha.app.repository.util.RepositoryUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
@Log4j2
public class ArticleRepositoryImpl extends GenericRepositoryImpl<Long, Article> implements ArticleRepository {

    @Override
    public List<Article> findAll(int pageNumber, int pageSize) {
        String hqlQuery = "SELECT a.id, a.uuid, a.dateAdded, a.title, SUBSTRING(a.content,1,200), a.user FROM Article a ORDER BY a.dateAdded DESC";
        Query query = entityManager.createQuery(hqlQuery);
        query.setFirstResult(RepositoryUtil.getStartPosition(pageNumber, pageSize));
        query.setMaxResults(pageSize);

        List<Article> articles = new ArrayList<>();
        List<Object> resultList = query.getResultList();
        for (Object object : resultList) {
            Article article = createArticle(object);
            articles.add(article);
        }
        return articles;
    }

    @Override
    public Article findByUuid(UUID uuid) {
        try {
            String hqlQuery = "SELECT a FROM Article a WHERE a.uuid=:uuid";
            Query query = entityManager.createQuery(hqlQuery);
            query.setParameter("uuid", uuid);
            return (Article) query.getSingleResult();
        } catch (NoResultException e) {
            log.error(e.getMessage(), e);
            throw new RepositoryException("Article with uuid:" + uuid + " does not exist", e);
        }
    }

    private Article createArticle(Object object) {
        Article article = new Article();
        Object[] objects = (Object[]) object;
        Long id = (Long) objects[0];
        article.setId(id);
        UUID uuid = (UUID) objects[1];
        article.setUuid(uuid);
        LocalDate date = (LocalDate) objects[2];
        article.setDateAdded(date);
        String title = (String) objects[3];
        article.setTitle(title);
        String content = (String) objects[4];
        article.setContent(content);
        User user = (User) objects[5];
        article.setUser(user);
        return article;
    }
}
