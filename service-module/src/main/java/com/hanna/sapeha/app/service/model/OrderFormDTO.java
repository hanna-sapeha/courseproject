package com.hanna.sapeha.app.service.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class OrderFormDTO {
    private Long itemId;
    @NotNull
    private Integer countOfItems;
}
