package com.hanna.sapeha.app.repository.impl;

import com.hanna.sapeha.app.repository.CommentRepository;
import com.hanna.sapeha.app.repository.model.Comment;
import org.springframework.stereotype.Repository;

@Repository
public class CommentRepositoryImpl extends GenericRepositoryImpl<Long, Comment> implements CommentRepository {
}
