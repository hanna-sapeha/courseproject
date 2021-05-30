package com.hanna.sapeha.app.service.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class ArticleFormDTO {
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateAdded;
    @NotNull
    @NotBlank
    @Size(min = 2, max = 200, message = "characters count should be in the range between 2 and 200")
    private String title;
    @NotNull
    @NotBlank
    @Size(min = 2, max = 1000, message = "characters count should be in the range between 2 and 1000")
    private String content;
}
