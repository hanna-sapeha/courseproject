package com.hanna.sapeha.app.repository.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@Entity
@Table(name = "item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column
    private String name;
    @Column(name = "unique_number")
    @Type(type = "uuid-char")
    @EqualsAndHashCode.Exclude
    private UUID uniqueNumber;
    @Column
    private BigDecimal price;
    @Column
    private String summary;
}
