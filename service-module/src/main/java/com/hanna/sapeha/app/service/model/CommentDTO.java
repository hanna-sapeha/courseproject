package com.hanna.sapeha.app.service.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CommentDTO {
    private Long id;
    private LocalDate dateAdded;
    private String content;
    private String userFirstname;
    private String userLastname;
}
