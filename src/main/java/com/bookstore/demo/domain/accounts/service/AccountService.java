package com.bookstore.demo.domain.accounts.service;

import com.bookstore.demo.domain.accounts.model.AccountCreateDTO;
import com.bookstore.demo.domain.accounts.model.AccountGetDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface AccountService {
    AccountGetDTO create(AccountCreateDTO account);
    AccountGetDTO getById(Long id) throws Exception;

    Page<AccountGetDTO> get(Pageable pageable);

    AccountGetDTO update(AccountCreateDTO account, Long id);

    void delete(Long id);

}
