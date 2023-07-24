package com.bookstore.demo.web;

import com.bookstore.demo.domain.roles.model.RoleGetDTO;
import com.bookstore.demo.domain.roles.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/role")
public class RoleController {
    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleGetDTO> getById(@PathVariable Long id) {
        RoleGetDTO roleGetDTO = roleService.getById(id);
        return ResponseEntity.ok(roleGetDTO);
    }

    @GetMapping
    public ResponseEntity<Page<RoleGetDTO>> get(Pageable pageable) {
        return  ResponseEntity.ok(roleService.getAll(pageable));
    }

}
