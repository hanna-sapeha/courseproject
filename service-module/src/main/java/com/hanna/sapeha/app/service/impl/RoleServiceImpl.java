package com.hanna.sapeha.app.service.impl;

import com.hanna.sapeha.app.repository.RoleRepository;
import com.hanna.sapeha.app.repository.model.Role;
import com.hanna.sapeha.app.service.RoleService;
import com.hanna.sapeha.app.service.converter.RoleConverter;
import com.hanna.sapeha.app.service.model.RoleDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final RoleConverter roleConverter;

    @Override
    @Transactional
    public List<RoleDTO> getAll() {
        List<Role> roles = roleRepository.findAll();
        return roleConverter.convert(roles);
    }
}
