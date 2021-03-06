package com.hanna.sapeha.app.service;

import com.hanna.sapeha.app.service.model.PageDTO;
import com.hanna.sapeha.app.service.model.ReviewDTO;
import com.hanna.sapeha.app.service.model.ReviewFormDTO;

import java.util.List;

public interface ReviewService {

    PageDTO<ReviewDTO> getAllByPagination(int pageNumber, int pageSize);

    void removeById(Long id);

    void changeStatus(List<Long> selectedIds, List<Long> allIds);

    ReviewDTO addReview(ReviewFormDTO review, Long idAuthorizedUser);
}
