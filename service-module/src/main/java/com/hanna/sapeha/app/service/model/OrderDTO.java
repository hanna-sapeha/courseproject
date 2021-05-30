package com.hanna.sapeha.app.service.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderDTO {
    private Long id;
    private String numberOfOrder;
    private String status;
    private String itemName;
    private String userIdentifier;
    private String userPhone;
    private Integer countOfItems;
    private BigDecimal finalPrice;
}
