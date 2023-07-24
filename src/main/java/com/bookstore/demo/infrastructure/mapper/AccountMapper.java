package com.bookstore.demo.infrastructure.mapper;

import com.bookstore.demo.domain.accounts.entity.Account;
import com.bookstore.demo.domain.accounts.model.AccountCreateDTO;
import com.bookstore.demo.domain.accounts.model.AccountGetDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    @Mapping(target="role", ignore = true)
    Account accountCreateDTOToAccount(AccountCreateDTO accountDTO);

    @Mapping(source="role.id", target="role")
    AccountGetDTO accountToAccountGetDTO(Account account);

    @Mapping(target="role", ignore = true)
    void updateAccountFromDto(AccountCreateDTO account, @MappingTarget Account entity);
}

