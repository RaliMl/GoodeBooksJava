package com.bookstore.demo.domain.roles.service;

import com.bookstore.demo.domain.roles.entity.Role;
import com.bookstore.demo.domain.roles.model.RoleGetDTO;
import com.bookstore.demo.domain.roles.repository.RoleRepository;
import com.bookstore.demo.exceptions.EntityNotFoundException;
import com.bookstore.demo.infrastructure.mapper.RoleMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    public final RoleMapper roleMapper;
    public final RoleRepository roleRepository;

    public RoleServiceImpl(RoleMapper roleMapper, RoleRepository roleRepository) {
        this.roleMapper = roleMapper;
        this.roleRepository = roleRepository;
    }

    private Role findRoleByID(Long id) {
        return roleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Role not found!"));
    }

    @Override
    public RoleGetDTO getById(Long id) {
        Role role = findRoleByID(id);
        return roleMapper.roleToRoleGetDTO(role);
    }

    @Override
    public Page<RoleGetDTO> getAll(Pageable pageable) {
        Page<Role> page = roleRepository.findAll(pageable);
        return page.map(entity -> {
            RoleGetDTO dto = roleMapper.roleToRoleGetDTO(entity); return dto;
        });
    }

}
