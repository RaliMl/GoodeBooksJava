package com.bookstore.demo.infrastructure.mapper;

import com.bookstore.demo.domain.roles.entity.Role;
import com.bookstore.demo.domain.roles.model.RoleGetDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleGetDTO roleToRoleGetDTO(Role role);
}
