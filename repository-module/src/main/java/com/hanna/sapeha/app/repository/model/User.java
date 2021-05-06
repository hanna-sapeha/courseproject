package com.hanna.sapeha.app.repository.model;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "user")
@Getter
@Setter
@EqualsAndHashCode(exclude = {"firstname", "patronymic", "email", "uniqueNumber"})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column(name = "unique_number")
    @Type(type = "uuid-char")
    private UUID uniqueNumber;
    @Column
    private String firstname;
    @Column
    private String lastname;
    @Column
    private String patronymic;
    @Column
    private String email;
    @Column
    private String password;
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_role")
    private Role role;
}
