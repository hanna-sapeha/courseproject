package com.hanna.sapeha.app.service.model;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class CommentFormDTO {
    @NotNull
    @Size(min = 2, max = 200, message = "characters count should be in the range between 2 and 200")
    private String content;
}
