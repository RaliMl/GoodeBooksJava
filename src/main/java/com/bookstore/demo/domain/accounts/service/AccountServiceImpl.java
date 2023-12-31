package com.bookstore.demo.domain.accounts.service;

import com.bookstore.demo.domain.accounts.entity.Account;
import com.bookstore.demo.domain.accounts.model.AccountCreateDTO;
import com.bookstore.demo.domain.accounts.model.AccountGetDTO;
import com.bookstore.demo.domain.accounts.repository.AccountRepository;
import com.bookstore.demo.domain.roles.entity.Role;
import com.bookstore.demo.domain.roles.repository.RoleRepository;
import com.bookstore.demo.exceptions.EmailException;
import com.bookstore.demo.infrastructure.mapper.AccountMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class AccountServiceImpl implements AccountService{
    public final AccountMapper accountMapper;
    public final AccountRepository accountRepository;
    public final RoleRepository roleRepository;
    public final PasswordEncoder passwordEncoder;

    public AccountServiceImpl(AccountMapper accountMapper, AccountRepository accountRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.accountMapper = accountMapper;
        this.accountRepository = accountRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private Account findAccountByID(Long id){
        return accountRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("The account was not found!"));
    }
    @Override
    public AccountGetDTO create(AccountCreateDTO accountDTO) {
        Long id = accountDTO.roleID();
        Role role = roleRepository.findById(id).orElseThrow();

        Account account = accountMapper.accountCreateDTOToAccount(accountDTO);
        account.setRole(role);

        String encodedPassword = passwordEncoder.encode(accountDTO.password());
        account.setPassword(encodedPassword);

        Account createdProject = accountRepository.save(account);

        return accountMapper.accountToAccountGetDTO(createdProject);
    }

    @Override
    public AccountGetDTO getById(Long id) {
        Account account = findAccountByID(id);
        return accountMapper.accountToAccountGetDTO(account);
    }

    @Override
    public Page<AccountGetDTO> get(Pageable pageable) {
        Page<Account> page = accountRepository.findAll(pageable);
        return page.map(entity -> {
            AccountGetDTO dto = accountMapper.accountToAccountGetDTO(entity); return dto;});
    }

    @Override
    public AccountGetDTO update(AccountCreateDTO account, Long id) {

        Account currentAccount = findAccountByID(id);
        if (!account.email().equals(currentAccount.getEmail()))
            throw new EmailException("Email cannot be updated!");

        if(account.roleID() != currentAccount.getRole().getId()){
            Role role = roleRepository.findById(account.roleID()).orElseThrow();
            currentAccount.setRole(role);
        }


        accountMapper.updateAccountFromDto(account, currentAccount);
        accountRepository.save(currentAccount);

        return accountMapper.accountToAccountGetDTO(currentAccount);

    }

    @Override
    public void delete(Long id) {
        if (!accountRepository.existsById(id)) {
            throw new EntityNotFoundException("The account was not found!");
        }
        accountRepository.deleteById(id);
    }
}
