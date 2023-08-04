package com.bookstore.demo.config.services;

import com.bookstore.demo.domain.accounts.entity.Account;
import com.bookstore.demo.domain.accounts.repository.AccountRepository;
import com.bookstore.demo.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        Account account = accountRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("Account was not found!"));
        String encodedPass = passwordEncoder.encode(account.getPassword());
        //account.setPassword(encodedPass);
        return new MyUserPrinciple(account);
    }

}

