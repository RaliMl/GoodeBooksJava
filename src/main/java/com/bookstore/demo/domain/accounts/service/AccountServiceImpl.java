package com.bookstore.demo.domain.accounts.service;

import com.bookstore.demo.domain.accounts.entity.Account;
import com.bookstore.demo.domain.accounts.model.AccountCreateDTO;
import com.bookstore.demo.domain.accounts.model.AccountGetDTO;
import com.bookstore.demo.domain.accounts.repository.AccountRepository;
import com.bookstore.demo.exceptions.EmailException;
import com.bookstore.demo.infrastructure.mapper.AccountMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class AccountServiceImpl implements AccountService{
    public final AccountMapper accountMapper;
    public final AccountRepository accountRepository;

    public AccountServiceImpl(AccountMapper accountMapper, AccountRepository accountRepository) {
        this.accountMapper = accountMapper;
        this.accountRepository = accountRepository;
    }

    private Account findAccountByID(Long id){
        return accountRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Account was not found!"));
    }
    @Override
    public AccountGetDTO create(AccountCreateDTO accountDTO) {
        Account account = accountMapper.accountCreateDTOToAccount(accountDTO);
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
            throw new EmailException("Email connot be updated!");

        accountMapper.updateAccountFromDto(account, currentAccount);
        accountRepository.save(currentAccount);

        return accountMapper.accountToAccountGetDTO(currentAccount);

    }

    @Override
    public void delete(Long id) {
        if (!accountRepository.existsById(id)) {
            throw new EntityNotFoundException("Account not found!");
        }
        accountRepository.deleteById(id);
    }
}
