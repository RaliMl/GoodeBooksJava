package com.bookstore.demo.domain.accounts.model;

import javax.validation.constraints.*;

public record AccountCreateDTO(
        @Email(message = "The entered email address is invalid!")
        @NotBlank
        String email,
        @NotBlank
        @Pattern(regexp = "(?=.*[A-Z])(?=.*[a-z])(?=.*[-'])*.{3,}", message = "The entered name is invalid!")
        String fullName,
        @NotBlank
        @Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=_]).{8,20}", message = "The entered password is invalid!")
        String password,
        Long roleID

) {
}
