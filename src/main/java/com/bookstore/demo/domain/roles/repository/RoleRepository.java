package com.bookstore.demo.domain.roles.repository;

import com.bookstore.demo.domain.roles.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
