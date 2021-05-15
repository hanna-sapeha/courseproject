package com.hanna.sapeha.app.repository;

import com.hanna.sapeha.app.repository.model.Role;
import com.hanna.sapeha.app.repository.model.enums.RolesEnum;

public interface RoleRepository extends GenericRepository<Long, Role> {
    Role findByName(RolesEnum roleName);
}
