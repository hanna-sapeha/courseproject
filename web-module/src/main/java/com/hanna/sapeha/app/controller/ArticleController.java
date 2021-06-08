package com.hanna.sapeha.app.controller;

import com.hanna.sapeha.app.controller.exception.ControllerException;
import com.hanna.sapeha.app.service.ArticleService;
import com.hanna.sapeha.app.service.model.ArticleDTO;
import com.hanna.sapeha.app.service.model.ArticleFormDTO;
import com.hanna.sapeha.app.service.model.PageDTO;
import com.hanna.sapeha.app.service.model.UserLogin;
import com.hanna.sapeha.app.util.ControllerUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

import static com.hanna.sapeha.app.constant.HandlerConstants.ARTICLES_URL;

@Controller
@RequestMapping(ARTICLES_URL)
@RequiredArgsConstructor
@Log4j2
public class ArticleController {
    private final ArticleService articleService;

    @GetMapping
    public String getAllNews(Model model,
                             @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                             @RequestParam(value = "page", required = false, defaultValue = "1") int pageNumber) {
        PageDTO<ArticleDTO> page = articleService.getAllByPagination(pageNumber, pageSize);
        model.addAttribute("page", page);
        UserLogin user = ControllerUtil.getAuthorizedUser();
        model.addAttribute("role", user.getRoleName());
        return "articles";
    }

    @GetMapping("/{uuid}")
    public String getArticle(Model model, @PathVariable UUID uuid) {
        ArticleDTO article = articleService.getArticleByUuid(uuid);
        model.addAttribute("article", article);
        UserLogin user = ControllerUtil.getAuthorizedUser();
        model.addAttribute("role", user.getRoleName());
        return "article";
    }

    @PostMapping("/delete")
    public String removeArticle(@RequestParam(value = "idArticleForDelete", required = false) Long id) {
        if (ControllerUtil.isAuthorizedAsSaleUser()) {
            articleService.removeById(id);
            return "redirect:" + ARTICLES_URL;
        } else {
            throw new ControllerException(String.format("User %s has not authority to remove article",
                    ControllerUtil.getAuthorizedUser().getUsername()));
        }
    }

    @PostMapping("/add")
    public String addArticle(@Valid ArticleFormDTO article, BindingResult errors) {
        if (ControllerUtil.isAuthorizedAsSaleUser()) {
            if (errors.hasErrors()) {
                return "add-article";
            } else {
                String authorizedUserEmail = ControllerUtil.getAuthorizedUser().getUsername();
                articleService.add(article, authorizedUserEmail);
                return "redirect:" + ARTICLES_URL;
            }
        } else {
            throw new ControllerException(String.format("User %s has not authority to remove article",
                    ControllerUtil.getAuthorizedUser().getUsername()));
        }
    }

    @GetMapping("/add")
    public String redirectToAddArticle(ArticleFormDTO article) {
        if (ControllerUtil.isAuthorizedAsSaleUser()) {
            return "add-article";
        } else {
            throw new ControllerException(String.format("User %s has not authority to remove article",
                    ControllerUtil.getAuthorizedUser().getUsername()));
        }
    }

    @GetMapping("/change/{uuid}")
    public String redirectToChangeArticle(@RequestParam(name = "articleIdForChange", required = false) Long id,
                                          ArticleFormDTO articleForm,
                                          Model model,
                                          @PathVariable UUID uuid) {
        if (ControllerUtil.isAuthorizedAsSaleUser()) {
            ArticleDTO article = articleService.getArticleByUuid(uuid);
            model.addAttribute("article", article);
            return "change-article";
        } else {
            throw new ControllerException(String.format("User %s has not authority to remove article",
                    ControllerUtil.getAuthorizedUser().getUsername()));
        }
    }

    @PostMapping("/change/{uuid}")
    public String changeArticle(@Valid ArticleFormDTO articleForm,
                                BindingResult errors,
                                @PathVariable UUID uuid) {
        if (ControllerUtil.isAuthorizedAsSaleUser()) {
            if (errors.hasErrors()) {
                return String.format("redirect:%s/%s", ARTICLES_URL, uuid);
            } else {
                ArticleDTO articleDTO = articleService.changeArticle(uuid, articleForm);
                return String.format("redirect:%s/%s", ARTICLES_URL, articleDTO.getUuid());
            }
        } else {
            throw new ControllerException(String.format("User %s has not authority to remove article",
                    ControllerUtil.getAuthorizedUser().getUsername()));
        }
    }
}
