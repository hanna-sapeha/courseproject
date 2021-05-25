package com.hanna.sapeha.app.repository.exception;

import javax.persistence.NoResultException;

public class RepositoryException extends RuntimeException {
    public RepositoryException(String message, NoResultException e) {
        super(message, e);
    }

    public RepositoryException(String message, IllegalArgumentException e) {
        super(message, e);
    }
}
