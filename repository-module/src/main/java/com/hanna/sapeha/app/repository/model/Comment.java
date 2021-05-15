package com.hanna.sapeha.app.repository.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "comment")
@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column(name = "date_added")
    @EqualsAndHashCode.Exclude
    private LocalDate dateAdded;
    private String content;
    @ManyToOne(optional = false,
            cascade = CascadeType.REFRESH)
    @JoinColumn(name = "user_id")
    @EqualsAndHashCode.Exclude
    private User user;
}
