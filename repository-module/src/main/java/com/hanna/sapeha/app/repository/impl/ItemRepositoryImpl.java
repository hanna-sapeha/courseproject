package com.hanna.sapeha.app.repository.impl;

import com.hanna.sapeha.app.repository.ItemRepository;
import com.hanna.sapeha.app.repository.exception.RepositoryException;
import com.hanna.sapeha.app.repository.model.Item;
import com.hanna.sapeha.app.repository.util.RepositoryUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.UUID;

import static com.hanna.sapeha.app.repository.constant.RepositoryConstants.ITEM_NAME_PARAMETER;
import static com.hanna.sapeha.app.repository.constant.RepositoryConstants.UNIQUE_NUMBER_PARAMETER;

@Repository
@Log4j2
public class ItemRepositoryImpl extends GenericRepositoryImpl<Long, Item> implements ItemRepository {

    @Override
    public List<Item> findAll(int pageNumber, int pageSize) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        countQuery.select(criteriaBuilder.count(countQuery.from(Item.class)));
        Long count = entityManager.createQuery(countQuery).getSingleResult();

        CriteriaQuery<Item> itemQuery = criteriaBuilder.createQuery(Item.class);
        Root<Item> itemRoot = itemQuery.from(Item.class);
        CriteriaQuery<Item> select = itemQuery.select(itemRoot)
                .orderBy(criteriaBuilder.asc(itemRoot.get(ITEM_NAME_PARAMETER)));
        TypedQuery<Item> typedQuery = entityManager.createQuery(select);
        if (pageSize < count.intValue()) {
            typedQuery.setFirstResult(RepositoryUtil.getStartPosition(pageNumber, pageSize));
            typedQuery.setMaxResults(pageSize);
            return typedQuery.getResultList();
        }
        return typedQuery.getResultList();
    }

    @Override
    public Item copy(Item item) {
        try {
            entityManager.detach(item);
            item.setId(null);
            UUID uuid = UUID.randomUUID();
            item.setUniqueNumber(uuid);
            item.setName(String.format("%s - Copy", item.getName()));
            entityManager.persist(item);
            return item;
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage(), e);
            throw new RepositoryException(String.format("Item with id %s does not exist", item.getId()), e);
        }
    }

    @Override
    public Item findByUuid(UUID uuid) {
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Item> query = criteriaBuilder.createQuery(Item.class);
            Root<Item> itemRoot = query.from(Item.class);
            query.select(itemRoot);
            ParameterExpression<UUID> parameter = criteriaBuilder.parameter(UUID.class);
            query.where(criteriaBuilder.equal(itemRoot.get(UNIQUE_NUMBER_PARAMETER), parameter));
            TypedQuery<Item> typedQuery = entityManager.createQuery(query);
            typedQuery.setParameter(parameter, uuid);
            return typedQuery.getSingleResult();
        } catch (NoResultException e) {
            log.error(e.getMessage(), e);
            throw new RepositoryException(String.format("Item with unique number '%s' does not exit", uuid), e);
        }
    }
}
