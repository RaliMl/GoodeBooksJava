package com.bookstore.demo.domain.accounts.model;

import javax.validation.constraints.*;

public record AccountGetDTO(
        Long id,
        @Email(message = "The entered email address is invalid!")
        @NotBlank
        String email,
        @NotBlank
        @Pattern(regexp = "(?=.*[A-Z])(?=.*[a-z])(?=.*[-'])*.{3,}", message = "The entered name is invalid!")
        String fullName,
        Long role
) {
}
