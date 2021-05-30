package com.hanna.sapeha.app.service.converter;

import com.hanna.sapeha.app.repository.model.Review;
import com.hanna.sapeha.app.repository.model.User;
import com.hanna.sapeha.app.service.model.ReviewDTO;
import com.hanna.sapeha.app.service.model.ReviewFormDTO;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ReviewConverterTest {
    private final ReviewConverter reviewConverter = new ReviewConverter();

    @Test
    void shouldConvertReviewToDtoAndReturnRightId() {
        Review review = new Review();
        long id = 1L;
        review.setId(id);
        ReviewDTO reviewDTO = reviewConverter.convert(review);
        assertEquals(id, reviewDTO.getId());
    }

    @Test
    void shouldConvertReviewToDtoAndReturnRightFeedback() {
        Review review = new Review();
        String feedback = "feedback";
        review.setFeedback(feedback);
        ReviewDTO reviewDTO = reviewConverter.convert(review);
        assertEquals(feedback, reviewDTO.getFeedback());
    }

    @Test
    void shouldConvertReviewToDtoAndReturnRightDate() {
        Review review = new Review();
        LocalDate date = LocalDate.now();
        review.setDateAdded(date);
        ReviewDTO reviewDTO = reviewConverter.convert(review);
        assertEquals(date, reviewDTO.getDateAdded());
    }

    @Test
    void shouldConvertReviewToDtoAndReturnUserFirstname() {
        Review review = new Review();
        User user = new User();
        String firstname = "firstname";
        user.setFirstname(firstname);
        review.setUser(user);
        ReviewDTO reviewDTO = reviewConverter.convert(review);
        assertEquals(firstname, reviewDTO.getUserFirstname());
    }

    @Test
    void shouldConvertReviewToDtoAndReturnUserLastname() {
        Review review = new Review();
        User user = new User();
        String lastname = "lastname";
        user.setLastname(lastname);
        review.setUser(user);
        ReviewDTO reviewDTO = reviewConverter.convert(review);
        assertEquals(lastname, reviewDTO.getUserLastname());
    }

    @Test
    void shouldConvertReviewToDtoAndReturnUserPatronymic() {
        Review review = new Review();
        User user = new User();
        String patronymic = "patronymic";
        user.setPatronymic(patronymic);
        review.setUser(user);
        ReviewDTO reviewDTO = reviewConverter.convert(review);
        assertEquals(patronymic, reviewDTO.getUserPatronymic());
    }

    @Test
    void shouldConvertReviewToDtoAndReturnIsActiveTrue() {
        Review review = new Review();
        review.setIsActive(true);
        ReviewDTO reviewDTO = reviewConverter.convert(review);
        assertTrue(reviewDTO.getIsActive());
    }

    @Test
    void shouldConvertReviewsToListDtoAndReturn() {
        Review review = new Review();
        long id = 1L;
        review.setId(id);
        User user = new User();
        review.setUser(user);
        List<Review> reviews = Collections.singletonList(review);
        List<ReviewDTO> reviewDTOS = reviewConverter.convert(reviews);
        assertEquals(id, reviewDTOS.get(0).getId());
    }

    @Test
    void shouldConvertReviewFormDTOToReviewAndReturnRightFeedback() {
        ReviewFormDTO reviewFormDTO = new ReviewFormDTO();
        String feedback = "feedback";
        reviewFormDTO.setFeedback(feedback);
        Review review = reviewConverter.convert(reviewFormDTO);
        assertEquals(feedback, review.getFeedback());
    }
}