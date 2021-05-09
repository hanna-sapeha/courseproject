package com.hanna.sapeha.app.service.model;

import lombok.Data;

import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
public class ArticleDTO {
    @Null
    private Long id;
    @Null
    private UUID uuid;
    @Null
    private LocalDate dateAdded;
    @Size(min = 2, max = 200, message = "characters count should be in the range between 2 and 200")
    private String title;
    @Size(min = 2, max = 1000, message = "characters count should be in the range between 2 and 1000")
    private String content;
    private String userFirstname;
    private String userLastname;
    @Null
    private List<CommentDTO> comments;
}
