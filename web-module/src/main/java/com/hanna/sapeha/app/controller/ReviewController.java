package com.hanna.sapeha.app.controller;

import com.hanna.sapeha.app.service.ReviewService;
import com.hanna.sapeha.app.service.model.PageDTO;
import com.hanna.sapeha.app.service.model.ReviewDTO;
import com.hanna.sapeha.app.service.model.ReviewFormDTO;
import com.hanna.sapeha.app.util.ControllerUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

import static com.hanna.sapeha.app.constant.HandlerConstants.ARTICLES_URL;
import static com.hanna.sapeha.app.constant.HandlerConstants.REVIEWS_URL;

@Controller
@RequestMapping(REVIEWS_URL)
@RequiredArgsConstructor
@Log4j2
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping
    public String getReviews(Model model,
                             @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                             @RequestParam(value = "page", required = false, defaultValue = "1") Integer pageNumber) {
        PageDTO<ReviewDTO> page = reviewService.getAllByPagination(pageNumber, pageSize);
        model.addAttribute("page", page);
        return "reviews";
    }

    @PostMapping("/delete")
    public String deleteItems(@RequestParam(value = "id") Long id) {
        reviewService.removeById(id);
        return "redirect:" + REVIEWS_URL;
    }

    @PostMapping("/change-status")
    public String changeStatus(@RequestParam(value = "selectedIds", required = false) List<Long> selectedIds,
                               @RequestParam(value = "allIds") List<Long> allIds) {
        reviewService.changeStatus(selectedIds, allIds);
        return "redirect:" + REVIEWS_URL;
    }

    @GetMapping("/add")
    public String redirectToAddReview(ReviewFormDTO review) {
        return "add-review";
    }

    @PostMapping("/add")
    public String addReview(@Valid ReviewFormDTO review,
                            BindingResult errors,
                            @RequestParam(value = "roleId", required = false) Long roleId) {
        if (errors.hasErrors()) {
            return "add-review";
        } else {
            Long idAuthorizedUser = ControllerUtil.getAuthorizedUser().getId();
            reviewService.addReview(review, idAuthorizedUser);
            return "redirect:" + ARTICLES_URL;
        }
    }

}
