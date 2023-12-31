package com.bookstore.demo.web;

import com.bookstore.demo.domain.accounts.model.AccountCreateDTO;
import com.bookstore.demo.domain.accounts.model.AccountGetDTO;
import com.bookstore.demo.domain.accounts.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/account")
public class AccountController {
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')")
    public ResponseEntity<AccountGetDTO> create (@RequestBody AccountCreateDTO accountDTO) {
        return ResponseEntity.ok(accountService.create(accountDTO));
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Page<AccountGetDTO>> get(Pageable pageable) {
        return  ResponseEntity.ok(accountService.get(pageable));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<AccountGetDTO> getById(@PathVariable Long id) throws Exception {

        AccountGetDTO task = accountService.getById(id);
        return ResponseEntity.ok(task);

    }
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') || (hasRole('ROLE_CLIENT') && @authenticationService.isCurrentlyLoggedUser(#id))")
    public ResponseEntity<AccountCreateDTO> update(@RequestBody AccountCreateDTO account, @PathVariable Long id) {

        accountService.update(account, id);
        return ResponseEntity.ok(account);

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') || (hasRole('ROLE_CLIENT') && @authenticationService.isCurrentlyLoggedUser(#id))")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        accountService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
