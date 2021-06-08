package com.hanna.sapeha.app.service.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
public class ArticleDTO {
    private Long id;
    private UUID uuid;
    private LocalDate dateAdded;
    private String title;
    private String content;
    private String userFirstname;
    private String userLastname;
    private List<CommentDTO> comments;
}
