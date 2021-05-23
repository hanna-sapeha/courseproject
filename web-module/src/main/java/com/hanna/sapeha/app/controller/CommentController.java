package com.hanna.sapeha.app.controller;

import com.hanna.sapeha.app.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

import static com.hanna.sapeha.app.constant.HandlerConstants.COMMENTS_URL;

@Controller
@RequiredArgsConstructor
@RequestMapping(COMMENTS_URL)
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/delete")
    public String deleteComment(@RequestParam(value = "idCommentForDelete", required = false) Long id,
                                @RequestParam(value = "uuidArticle", required = false) UUID uuidArticle) {
        commentService.removeById(id);
        return "redirect:/articles/" + uuidArticle;
    }
}
