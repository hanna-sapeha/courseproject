package com.hanna.sapeha.app.service.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class ItemDTO {
    private Long id;
    private String name;
    private UUID uniqueNumber;
    private BigDecimal price;
    private String summary;
}
