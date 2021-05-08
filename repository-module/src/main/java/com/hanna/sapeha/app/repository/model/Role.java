package com.hanna.sapeha.app.repository.model;


import com.hanna.sapeha.app.repository.model.enums.RolesEnum;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "role")
@Getter
@Setter
@EqualsAndHashCode
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column(name = "name")
    @Enumerated(EnumType.STRING)
    private RolesEnum roleName;

}
