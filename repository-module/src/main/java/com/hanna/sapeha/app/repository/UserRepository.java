package com.hanna.sapeha.app.repository;

import com.hanna.sapeha.app.repository.model.User;

import java.util.List;

public interface UserRepository extends GenericRepository<Long, User> {
    User getUserByEmail(String email);

    List<User> findAll(int pageNumber, int pageSize);

    Long getCountUsers();
}
