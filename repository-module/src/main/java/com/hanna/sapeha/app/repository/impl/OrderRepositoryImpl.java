package com.hanna.sapeha.app.repository.impl;

import com.hanna.sapeha.app.repository.OrderRepository;
import com.hanna.sapeha.app.repository.exception.RepositoryException;
import com.hanna.sapeha.app.repository.model.Order;
import com.hanna.sapeha.app.repository.model.User;
import com.hanna.sapeha.app.repository.util.RepositoryUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import java.util.List;

import static com.hanna.sapeha.app.repository.constant.RepositoryConstants.EMAIL_PARAMETER;
import static com.hanna.sapeha.app.repository.constant.RepositoryConstants.NUMBER_OF_ORDER_PARAMETER;

@Repository
@Log4j2
public class OrderRepositoryImpl extends GenericRepositoryImpl<Long, Order> implements OrderRepository {

    @Override
    public List<Order> findAll(Integer pageNumber, Integer pageSize) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        countQuery.select(criteriaBuilder.count(countQuery.from(Order.class)));
        Long count = entityManager.createQuery(countQuery).getSingleResult();

        CriteriaQuery<Order> orderQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> orderRoot = orderQuery.from(Order.class);
        CriteriaQuery<Order> select = orderQuery.select(orderRoot);

        TypedQuery<Order> typedQuery = entityManager.createQuery(select);
        if (pageSize < count.intValue()) {
            typedQuery.setFirstResult(RepositoryUtil.getStartPosition(pageNumber, pageSize));
            typedQuery.setMaxResults(pageSize);
            return typedQuery.getResultList();
        }
        return typedQuery.getResultList();
    }

    @Override
    public Order findByNumber(String numberOfOrder) {
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Order> query = criteriaBuilder.createQuery(Order.class);
            Root<Order> orderRoot = query.from(Order.class);
            query.select(orderRoot);
            ParameterExpression<String> parameter = criteriaBuilder.parameter(String.class);
            query.where(criteriaBuilder.equal(orderRoot.get(NUMBER_OF_ORDER_PARAMETER), parameter));
            TypedQuery<Order> typedQuery = entityManager.createQuery(query);
            typedQuery.setParameter(parameter, numberOfOrder);
            return typedQuery.getSingleResult();
        } catch (NoResultException e) {
            log.error(e.getMessage(), e);
            throw new RepositoryException(String.format("Order with number '%s' does not exit", numberOfOrder), e);
        }
    }

    @Override
    public List<Order> findAllForUser(int pageNumber, int pageSize, Long idUser) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        countQuery.select(criteriaBuilder.count(countQuery.from(Order.class)));
        Long count = entityManager.createQuery(countQuery).getSingleResult();

        CriteriaQuery<Order> orderQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> orderRoot = orderQuery.from(Order.class);
        Join<Object, User> join = orderRoot.join("user");
        CriteriaQuery<Order> select = orderQuery.select(orderRoot);
        orderQuery.where(criteriaBuilder.equal(join.get("id"), idUser));

        TypedQuery<Order> typedQuery = entityManager.createQuery(select);
        if (pageSize < count.intValue()) {
            typedQuery.setFirstResult(RepositoryUtil.getStartPosition(pageNumber, pageSize));
            typedQuery.setMaxResults(pageSize);
            return typedQuery.getResultList();
        }
        return typedQuery.getResultList();
    }
}
