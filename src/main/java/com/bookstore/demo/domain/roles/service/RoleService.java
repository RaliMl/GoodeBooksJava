package com.bookstore.demo.domain.roles.service;

import com.bookstore.demo.domain.roles.model.RoleGetDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface RoleService {
    RoleGetDTO getById(Long id);
    Page<RoleGetDTO> getAll(Pageable pageable);

}
