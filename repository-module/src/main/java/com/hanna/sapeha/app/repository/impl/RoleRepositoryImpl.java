package com.hanna.sapeha.app.repository.impl;

import com.hanna.sapeha.app.repository.RoleRepository;
import com.hanna.sapeha.app.repository.model.Role;
import org.springframework.stereotype.Repository;

@Repository
public class RoleRepositoryImpl extends GenericRepositoryImpl<Long, Role> implements RoleRepository {
}
