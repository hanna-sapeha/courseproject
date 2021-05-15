package com.hanna.sapeha.app.controller;

import com.hanna.sapeha.app.service.ArticleService;
import com.hanna.sapeha.app.service.model.ArticleDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.hanna.sapeha.app.constant.HandlerConstants.API_URL;
import static com.hanna.sapeha.app.constant.HandlerConstants.ARTICLE_URL;

@RestController
@RequestMapping(API_URL + ARTICLE_URL)
@RequiredArgsConstructor
@Log4j2
public class ArticleAPIController {
    private final ArticleService articleService;

    @GetMapping
    public List<ArticleDTO> getArticles() {
        return articleService.getArticles();
    }

    @GetMapping("/{id}")
    public ArticleDTO getArticle(@PathVariable Long id) {
        return articleService.getArticle(id);
    }

    @PostMapping
    public ResponseEntity<Void> addArticle(@RequestBody @Valid ArticleDTO article,
                                           BindingResult errors) {
        if (!errors.hasErrors()) {
            articleService.add(article);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            log.error("Article filling error: {}", errors);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeArticle(@PathVariable Long id) {
        articleService.removeById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

