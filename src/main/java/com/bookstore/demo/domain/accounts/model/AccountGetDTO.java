package com.bookstore.demo.domain.accounts.model;

public record AccountGetDTO(
        Long id,
        String email,
        String fullName,
        Long role
) {
}
