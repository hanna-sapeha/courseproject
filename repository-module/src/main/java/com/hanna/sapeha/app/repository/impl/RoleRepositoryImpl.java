package com.hanna.sapeha.app.repository.impl;

import com.hanna.sapeha.app.repository.RoleRepository;
import com.hanna.sapeha.app.repository.model.Role;
import com.hanna.sapeha.app.repository.model.enums.RolesEnum;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

import static com.hanna.sapeha.app.repository.constant.RepositoryConstants.ROLE_PARAMETER;

@Repository
@Log4j2
public class RoleRepositoryImpl extends GenericRepositoryImpl<Long, Role> implements RoleRepository {

    @Override
    public Role findByName(RolesEnum roleName) {
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Role> query = criteriaBuilder.createQuery(Role.class);
            Root<Role> roleRoot = query.from(Role.class);
            query.select(roleRoot);
            ParameterExpression<RolesEnum> parameter = criteriaBuilder.parameter(RolesEnum.class);
            query.where(criteriaBuilder.equal(roleRoot.get(ROLE_PARAMETER), parameter));
            TypedQuery<Role> typedQuery = entityManager.createQuery(query);
            typedQuery.setParameter(parameter, roleName);
            return typedQuery.getSingleResult();
        } catch (NoResultException e) {
            log.info("Role with name '{}' does not exit", roleName);
            return null;
        }
    }
}
