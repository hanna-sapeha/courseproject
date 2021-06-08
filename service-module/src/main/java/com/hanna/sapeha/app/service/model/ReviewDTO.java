package com.hanna.sapeha.app.service.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ReviewDTO {
    private Long id;
    private String feedback;
    private LocalDate dateAdded;
    private String userFirstname;
    private String userLastname;
    private String userPatronymic;
    private Boolean isActive;
}
