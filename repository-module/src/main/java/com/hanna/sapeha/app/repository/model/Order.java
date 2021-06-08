package com.hanna.sapeha.app.repository.model;

import com.hanna.sapeha.app.repository.model.enums.StatusEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private StatusEnum status;
    @Column(name = "number_of_order")
    private String numberOfOrder;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "item_id")
    @EqualsAndHashCode.Exclude
    private Item item;
    @Column(name = "count_of_item")
    private Integer countOfItems;
    @Column(name = "final_price")
    private BigDecimal finalPrice;
    @Column
    @EqualsAndHashCode.Exclude
    private LocalDate date;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    @EqualsAndHashCode.Exclude
    private User user;
}
