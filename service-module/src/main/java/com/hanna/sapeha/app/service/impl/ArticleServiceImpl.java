package com.hanna.sapeha.app.service.impl;

import com.hanna.sapeha.app.repository.ArticleRepository;
import com.hanna.sapeha.app.repository.UserRepository;
import com.hanna.sapeha.app.repository.model.Article;
import com.hanna.sapeha.app.repository.model.User;
import com.hanna.sapeha.app.service.ArticleService;
import com.hanna.sapeha.app.service.converter.ArticleConverter;
import com.hanna.sapeha.app.service.exception.ServiceException;
import com.hanna.sapeha.app.service.model.ArticleDTO;
import com.hanna.sapeha.app.service.model.PageDTO;
import com.hanna.sapeha.app.service.util.ServiceUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final ArticleConverter articleConverter;

    @Override
    @Transactional
    public PageDTO<ArticleDTO> getAllByPagination(int pageNumber, int pageSize) {
        PageDTO<ArticleDTO> page = new PageDTO<>();
        List<Article> articles = articleRepository.findAll(pageNumber, pageSize);
        List<ArticleDTO> articleDTOS = articleConverter.convertEntitiesToDTO(articles);
        page.getObjects().addAll(articleDTOS);
        Long countNews = articleRepository.getCount();
        List<Integer> numbersOfPages = IntStream.rangeClosed(1, ServiceUtil.getNumbersOfPages(pageSize, countNews))
                .boxed()
                .collect(Collectors.toList());
        page.getNumbersOfPage().addAll(numbersOfPages);
        return page;
    }

    @Override
    @Transactional
    public ArticleDTO getArticle(Long id) {
        Article article = articleRepository.findById(id);
        if (Objects.nonNull(article)) {
            return articleConverter.convertEntityToDTO(article);
        } else {
            throw new ServiceException("Article with id '" + id + "' does not exist");
        }
    }

    @Override
    @Transactional
    public ArticleDTO getArticleByUuid(UUID uuid) {
        Article article = articleRepository.findByUuid(uuid);
        if (Objects.nonNull(article)) {
            return articleConverter.convertEntityToDTO(article);
        } else {
            throw new ServiceException("Article with uuid '" + uuid + "' does not exist");
        }
    }

    @Override
    @Transactional
    public void removeById(Long id) {
        Article article = articleRepository.findById(id);
        if (Objects.nonNull(article)) {
            articleRepository.remove(article);
        }
    }

    @Override
    @Transactional
    public List<ArticleDTO> getArticles() {
        List<Article> articles = articleRepository.findAll();
        return articleConverter.convertEntitiesToDTO(articles);
    }

    @Override
    @Transactional
    public ArticleDTO add(ArticleDTO articleDTO) {
        Article article = articleConverter.convertDTOToEntity(articleDTO);
        User user = userRepository.findById(8L);
        article.setUser(user);
        UUID uuid = UUID.randomUUID();
        article.setUuid(uuid);
        LocalDate date = LocalDate.now();
        article.setDateAdded(date);
        articleRepository.persist(article);
        return articleConverter.convertEntityToDTO(article);
    }
}
