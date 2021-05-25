package com.hanna.sapeha.app.service.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.NonNull;

import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.UUID;

@Getter
@Setter
public class UserFormDTO {
    @NonNull
    @Size(min = 2, max = 20, message = "characters count should be in the range between 2 and 20")
    private String firstname;
    @NonNull
    @Size(min = 2, max = 40, message = "characters count should be in the range between 2 and 40")
    private String lastname;
    @NonNull
    @Size(min = 2, max = 40, message = "characters count should be in the range between 2 and 40")
    private String patronymic;
    @NonNull
    @Pattern(regexp = "^[-\\w.]+@([A-z0-9][-A-z0-9]+\\.)+[A-z]{2,4}$",
            message = "invalid e-mail, should match the template 'example@example.ex'")
    private String email;
    private String roleName;
}
