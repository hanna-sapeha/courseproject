package com.hanna.sapeha.app.service.impl;

import com.hanna.sapeha.app.repository.CommentRepository;
import com.hanna.sapeha.app.repository.model.Comment;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {
    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentServiceImpl commentService;

    @Test
    void shouldRemoveComment() {
        long id = 1L;
        Comment comment = new Comment();
        comment.setId(id);

        when(commentRepository.findById(id)).thenReturn(comment);

        Long removedCommentsId = commentService.removeById(id);
        assertEquals(id, removedCommentsId);
    }
}