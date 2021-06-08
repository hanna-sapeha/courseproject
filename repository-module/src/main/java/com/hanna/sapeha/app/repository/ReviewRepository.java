package com.hanna.sapeha.app.repository;

import com.hanna.sapeha.app.repository.model.Review;

import java.util.List;

public interface ReviewRepository extends GenericRepository<Long, Review> {

    List<Review> findAll(int pageNumber, int pageSize);
}
