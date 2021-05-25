package com.hanna.sapeha.app.repository.impl;

import com.hanna.sapeha.app.repository.GenericRepository;
import com.hanna.sapeha.app.repository.exception.RepositoryException;
import lombok.extern.log4j.Log4j2;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.lang.reflect.ParameterizedType;
import java.util.List;

@Log4j2
public abstract class GenericRepositoryImpl<I, T> implements GenericRepository<I, T> {
    @PersistenceContext
    protected EntityManager entityManager;
    protected Class<T> entityClass;

    @SuppressWarnings("unchecked")
    public GenericRepositoryImpl() {
        ParameterizedType genericClass = (ParameterizedType) getClass().getGenericSuperclass();
        this.entityClass = (Class<T>) genericClass.getActualTypeArguments()[1];
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> findAll() {
        String queryString = "FROM " + entityClass.getName();
        Query query = entityManager.createQuery(queryString);
        return query.getResultList();
    }

    @Override
    public void persist(T entity) {
        entityManager.persist(entity);
    }

    @Override
    public T findById(I id) {
        try {
            return entityManager.find(entityClass, id);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage(), e);
            throw new RepositoryException(String.format("%s with id %s does not exist", entityClass.getName(), id), e);
        }
    }

    @Override
    public void remove(T entity) {
        entityManager.remove(entity);
    }

    @Override
    @SuppressWarnings("unchecked")
    public I getCount() {
        String queryString = "SELECT COUNT(*) FROM " + entityClass.getName();
        Query query = entityManager.createQuery(queryString);
        return (I) query.getSingleResult();
    }
}
