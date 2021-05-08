package com.hanna.sapeha.app.repository.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "review")
@Getter
@Setter
@EqualsAndHashCode(exclude = {"feedback", "user"})
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_user")
    private User user;
    @Column
    private String feedback;
    @Column(name = "date_added")
    private LocalDate dateAdded;
    @Column(name = "is_active", columnDefinition = "BIT (1) ")
    private Boolean isActive;
}
