package com.hanna.sapeha.app.controller;

import com.hanna.sapeha.app.service.ReviewService;
import com.hanna.sapeha.app.service.model.PageDTO;
import com.hanna.sapeha.app.service.model.ReviewDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/reviews")
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
        return "redirect:/reviews";
    }

    @PostMapping("/change-status")
    public String changeStatus(@RequestParam(value = "selectedIds") List<Long> selectedIds,
                               @RequestParam(value = "allIds") List<Long> allIds) {
        reviewService.changeStatus(selectedIds, allIds);
        return "redirect:/reviews";
    }
}
