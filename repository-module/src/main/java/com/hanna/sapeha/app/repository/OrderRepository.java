package com.hanna.sapeha.app.repository;

import com.hanna.sapeha.app.repository.model.Order;

import java.util.List;

public interface OrderRepository extends GenericRepository<Long, Order> {
    List<Order> findAll(Integer pageNumber, Integer pageSize);

    Order findByNumber(String numberOfOrder);

    List<Order> findAllForUser(int pageNumber, int pageSize, Long idUser);
}
