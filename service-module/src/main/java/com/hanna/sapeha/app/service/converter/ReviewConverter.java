package com.hanna.sapeha.app.service.converter;

import com.hanna.sapeha.app.repository.model.Review;
import com.hanna.sapeha.app.service.model.ReviewDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class ReviewConverter {

    public List<ReviewDTO> convert(List<Review> reviews) {
        return reviews.stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

    public ReviewDTO convert(Review review) {
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setId(review.getId());
        reviewDTO.setFeedback(review.getFeedback());
        reviewDTO.setDateAdded(review.getDateAdded());
        if (Objects.nonNull(review.getUser())) {
            reviewDTO.setUserFirstname(review.getUser().getFirstname());
            reviewDTO.setUserLastname(review.getUser().getLastname());
            reviewDTO.setUserPatronymic(review.getUser().getPatronymic());
        }
        reviewDTO.setIsActive(review.getIsActive());
        return reviewDTO;
    }
}
