package com.hanna.sapeha.app.service.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleDTO {
    private Long id;
    private String roleName;
    private String roleEnumName;
}
