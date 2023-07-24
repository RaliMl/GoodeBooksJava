package com.bookstore.demo.infrastructure.mapper;

import com.bookstore.demo.domain.accounts.entity.Account;
import com.bookstore.demo.domain.accounts.model.AccountCreateDTO;
import com.bookstore.demo.domain.accounts.model.AccountGetDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    Account accountCreateDTOToAccount(AccountCreateDTO accountDTO);

    AccountGetDTO accountToAccountGetDTO(Account account);
    void updateAccountFromDto(AccountCreateDTO account, @MappingTarget Account entity);
}

