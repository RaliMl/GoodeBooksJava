package com.bookstore.demo.domain.roles.model;

import com.bookstore.demo.domain.accounts.entity.Account;

import javax.validation.constraints.Pattern;
import java.util.List;

public record RoleGetDTO(
        @Pattern(regexp = "^[a-zA-Z0-9_]*.{3,}$", message = "The name you entered is invalid!")
        String name,
        List<Account> accounts

) {
}
