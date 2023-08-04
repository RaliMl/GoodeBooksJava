package com.bookstore.demo.config.services;

import com.bookstore.demo.domain.accounts.entity.Account;
import com.bookstore.demo.domain.accounts.repository.AccountRepository;
import com.bookstore.demo.exceptions.EntityNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final AccountRepository accountRepository;

    public AuthenticationService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public boolean isCurrentlyLoggedUser(Long id) {
        UserDetails origUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Account account = accountRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("The account was not found!"));
        if(account.getEmail().equals(origUser.getUsername()))
            return true;
        return false;
    }
}
