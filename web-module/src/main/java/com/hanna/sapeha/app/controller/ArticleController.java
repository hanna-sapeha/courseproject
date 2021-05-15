package com.hanna.sapeha.app.controller;

import com.hanna.sapeha.app.service.ArticleService;
import com.hanna.sapeha.app.service.model.ArticleDTO;
import com.hanna.sapeha.app.service.model.PageDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

import static com.hanna.sapeha.app.constant.HandlerConstants.ARTICLE_URL;

@Controller
@RequestMapping(ARTICLE_URL)
@RequiredArgsConstructor
@Log4j2
public class ArticleController {
    private final ArticleService articleService;

    @GetMapping
    public String getAllNews(Model model,
                             @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                             @RequestParam(value = "page", required = false, defaultValue = "1") int pageNumber) {
        PageDTO<ArticleDTO> page = articleService.getAllByPagination(pageNumber, pageSize);
        log.info("{}", page.getObjects());
        model.addAttribute("page", page);

        return "articles";
    }

    @GetMapping("/{uuid}")
    public String getArticle(Model model, @PathVariable UUID uuid) {
        ArticleDTO article = articleService.getArticleByUuid(uuid);
        model.addAttribute("article", article);
        return "article";
    }
}
