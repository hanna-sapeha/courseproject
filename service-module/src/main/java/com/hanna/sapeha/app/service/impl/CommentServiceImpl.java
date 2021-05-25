package com.hanna.sapeha.app.service.impl;

import com.hanna.sapeha.app.repository.CommentRepository;
import com.hanna.sapeha.app.repository.model.Comment;
import com.hanna.sapeha.app.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    @Override
    @Transactional
    public Long removeById(Long id) {
        Comment comment = commentRepository.findById(id);
        commentRepository.remove(comment);
        return comment.getId();
    }
}
