package com.hanna.sapeha.app.service.impl;

import com.hanna.sapeha.app.repository.ReviewRepository;
import com.hanna.sapeha.app.repository.UserRepository;
import com.hanna.sapeha.app.repository.model.Review;
import com.hanna.sapeha.app.repository.model.User;
import com.hanna.sapeha.app.service.ReviewService;
import com.hanna.sapeha.app.service.converter.ReviewConverter;
import com.hanna.sapeha.app.service.exception.ServiceException;
import com.hanna.sapeha.app.service.model.PageDTO;
import com.hanna.sapeha.app.service.model.ReviewDTO;
import com.hanna.sapeha.app.service.model.ReviewFormDTO;
import com.hanna.sapeha.app.service.util.ServiceUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
@Log4j2
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewConverter reviewConverter;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public PageDTO<ReviewDTO> getAllByPagination(int pageNumber, int pageSize) {
        PageDTO<ReviewDTO> page = new PageDTO<>();
        List<Review> reviews = reviewRepository.findAll(pageNumber, pageSize);
        List<ReviewDTO> reviewDTOs = reviewConverter.convert(reviews);
        page.getObjects().addAll(reviewDTOs);
        Long countReviews = reviewRepository.getCount();
        List<Integer> numbersOfPages = IntStream.rangeClosed(1, ServiceUtil.getNumbersOfPages(pageSize, countReviews))
                .boxed()
                .collect(Collectors.toList());
        page.getNumbersOfPage().addAll(numbersOfPages);
        return page;
    }

    @Override
    @Transactional
    public void removeById(Long id) {
        Review review = reviewRepository.findById(id);
        if (Objects.nonNull(review)) {
            reviewRepository.remove(review);
        } else {
            throw new ServiceException("Review does not exist");
        }
    }

    @Override
    @Transactional
    public void changeStatus(List<Long> selectedIds, List<Long> allIds) {
        if (Objects.nonNull(selectedIds)) {
            for (Long id : selectedIds) {
                Review review = reviewRepository.findById(id);
                review.setIsActive(true);
                allIds.remove(id);
            }
        }
        for (Long id : allIds) {
            Review review = reviewRepository.findById(id);
            review.setIsActive(false);
        }
    }

    @Override
    @Transactional
    public ReviewDTO addReview(ReviewFormDTO reviewDTO, Long idAuthorizedUser) {
        User user = userRepository.findById(idAuthorizedUser);
        Review review = reviewConverter.convert(reviewDTO);
        review.setDateAdded(LocalDate.now());
        review.setUser(user);
        review.setIsActive(false);
        reviewRepository.persist(review);
        return reviewConverter.convert(review);
    }
}
