package com.hanna.sapeha.app.service.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.math.BigDecimal;

@Getter
@Setter
public class OrderFormDTO {
    private Long itemId;
    @NotNull
    private Integer countOfItems;
}
