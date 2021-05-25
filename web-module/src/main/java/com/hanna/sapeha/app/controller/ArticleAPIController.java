package com.hanna.sapeha.app.controller;

import com.hanna.sapeha.app.controller.model.ErrorDTO;
import com.hanna.sapeha.app.service.ArticleService;
import com.hanna.sapeha.app.service.model.ArticleDTO;
import com.hanna.sapeha.app.service.model.ArticleFormDTO;
import com.hanna.sapeha.app.util.ControllerUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static com.hanna.sapeha.app.constant.HandlerConstants.API_URL;
import static com.hanna.sapeha.app.constant.HandlerConstants.ARTICLES_URL;

@RestController
@RequestMapping(API_URL + ARTICLES_URL)
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
    public ResponseEntity<ErrorDTO> addArticle(@RequestBody @Valid ArticleFormDTO article,
                                               BindingResult errors) {
        if (!errors.hasErrors()) {
            String authorizedUserEmail = ControllerUtil.getAuthorizedUser().getUsername();
            articleService.add(article, authorizedUserEmail);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            ErrorDTO error = new ErrorDTO();
            List<String> errorMessages = errors.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            error.getErrors().addAll(errorMessages);
            log.error("Article filling error: {}", errors);
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeArticle(@PathVariable Long id) {
        articleService.removeById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

