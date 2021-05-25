package com.hanna.sapeha.app.service.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Getter
@Setter
public class ItemFormDTO {
    @NotBlank
    @NotNull
    @Size(min = 2, max = 200, message = "characters count should be in the range between 2 and 40")
    private String name;
    @NotNull
    @DecimalMin(value = "0.1")
    private BigDecimal price;
    @NotBlank
    @NotNull
    @Size(min = 2, max = 200, message = "characters count should be in the range between 2 and 200")
    private String summary;
}
