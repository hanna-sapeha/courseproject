package com.hanna.sapeha.app.service.converter;

import com.hanna.sapeha.app.repository.model.Comment;
import com.hanna.sapeha.app.repository.model.User;
import com.hanna.sapeha.app.service.model.CommentDTO;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class CommentConverter implements GeneralConverter<Comment, CommentDTO> {

    @Override
    public CommentDTO convertEntityToDTO(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setDateAdded(comment.getDateAdded());
        commentDTO.setContent(comment.getContent());
        User commentOwner = comment.getUser();
        if (Objects.nonNull(commentOwner)) {
            commentDTO.setUserFirstname(commentOwner.getFirstname());
            commentDTO.setUserLastname(commentOwner.getLastname());
        }
        return commentDTO;
    }

    @Override
    public List<CommentDTO> convertEntitiesToDTO(List<Comment> comments) {
        return comments.stream()
                .map(this::convertEntityToDTO)
                .sorted(Comparator.comparing(CommentDTO::getDateAdded))
                .collect(Collectors.toList());
    }

    @Override
    public Comment convertDTOToEntity(CommentDTO commentDTO) {
        Comment comment = new Comment();
        comment.setContent(commentDTO.getContent());
        return comment;
    }
}
