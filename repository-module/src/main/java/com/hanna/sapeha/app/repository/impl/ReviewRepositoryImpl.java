package com.hanna.sapeha.app.repository.impl;

import com.hanna.sapeha.app.repository.ReviewRepository;
import com.hanna.sapeha.app.repository.model.Review;
import com.hanna.sapeha.app.repository.util.RepositoryUtil;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class ReviewRepositoryImpl extends GenericRepositoryImpl<Long, Review> implements ReviewRepository {

    @Override
    public List<Review> findAll(int pageNumber, int pageSize) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        countQuery.select(criteriaBuilder.count(countQuery.from(Review.class)));
        Long count = entityManager.createQuery(countQuery).getSingleResult();

        CriteriaQuery<Review> userQuery = criteriaBuilder.createQuery(Review.class);
        Root<Review> reviewRoot = userQuery.from(Review.class);
        CriteriaQuery<Review> select = userQuery.select(reviewRoot);

        TypedQuery<Review> typedQuery = entityManager.createQuery(select);
        if (pageSize < count.intValue()) {
            typedQuery.setFirstResult(RepositoryUtil.getStartPosition(pageNumber, pageSize));
            typedQuery.setMaxResults(pageSize);
            return typedQuery.getResultList();
        }
        return typedQuery.getResultList();
    }
}
