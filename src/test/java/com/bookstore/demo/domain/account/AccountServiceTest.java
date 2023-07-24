package com.bookstore.demo.domain.account;

import com.bookstore.demo.domain.accounts.entity.Account;
import com.bookstore.demo.domain.accounts.model.AccountCreateDTO;
import com.bookstore.demo.domain.accounts.model.AccountGetDTO;
import com.bookstore.demo.domain.accounts.repository.AccountRepository;
import com.bookstore.demo.domain.accounts.service.AccountServiceImpl;
import com.bookstore.demo.exceptions.EmailException;
import com.bookstore.demo.infrastructure.mapper.AccountMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityNotFoundException;


@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private AccountMapper accountMapper;
    @InjectMocks
    private AccountServiceImpl accountService;

    private AccountCreateDTO accountDTO;
    private Account account;
    private AccountGetDTO accountGetDTO;


    @BeforeEach
    public void Init() {
        accountDTO = new AccountCreateDTO("rml@gmail.com", "Ralitsa MLadenova", "lala123@");
        account = new Account("rml@gmail.com", "Ralitsa MLadenova", "lala123@");
        account.setId(1L);
        accountGetDTO = new AccountGetDTO(1L, "rml@gmail.com", "Ralitsa MLadenova");
    }

    @Test
    void givenValidAccount_whenSaving_thenOK() {
        when(accountMapper.accountCreateDTOToAccount(any(AccountCreateDTO.class))).thenReturn(account);

        when(accountRepository.save(any(Account.class))).thenReturn(account);

        when(accountMapper.accountToAccountGetDTO(any(Account.class))).thenReturn(accountGetDTO);

        AccountGetDTO created = accountService.create(accountDTO);
        assertThat(created.fullName(), is(accountMapper.accountCreateDTOToAccount(accountDTO).getFullName()));
        Mockito.verify(accountRepository).save(accountMapper.accountCreateDTOToAccount(accountDTO));
    }

    @Test
    void givenValidID_whenGet_thenOK() {
        Long id = 1L;

        when(accountRepository.findById(anyLong())).thenReturn(Optional.of(account));

        when(accountMapper.accountToAccountGetDTO(any(Account.class))).thenReturn(accountGetDTO);

        AccountGetDTO found = accountService.getById(id);
        assertThat(found.fullName(), is(account.getFullName()));
        Mockito.verify(accountRepository).findById(id);
    }

    @Test
    void givenValidID_whenGetAll_thenOK() {
        Pageable pageable = PageRequest.of(0, 20);
        List<Account> list = new ArrayList<>();
        list.add(account);
        Page<Account> page = new PageImpl<>(list);

        when(accountRepository.findAll(any(Pageable.class))).thenReturn(page);

        when(accountMapper.accountToAccountGetDTO(any(Account.class))).thenReturn(accountGetDTO);

        Page<AccountGetDTO> res = accountService.get(pageable);
        List<AccountGetDTO> resList = res.getContent();
        assertThat(page.getSize(), is(res.getSize()));
        assertThat(page.getSort(), is(res.getSort()));
        assertThat(list.get(0).getFullName(), is(resList.get(0).fullName()));
        assertThat(list.get(0).getEmail(), is(resList.get(0).email()));
        assertThat(list.get(0).getId(), is(resList.get(0).id()));
        assertThat(list.get(0).getFullName(), is(resList.get(0).fullName()));
    }

    @SneakyThrows
    @Test
    void givenValidAccount_whenUpdating_thenOK() {
        Long id = 1L;
        Account account1 = new Account("rml@gmail.com", "Ralitsa MLadenova", "lala123@");
        Account newAcc = new Account("rml@gmail.com", "Deyana MLadenova", "lala123@");

        when(accountRepository.findById(anyLong())).thenReturn(Optional.of(account1));

        doNothing().when(accountMapper).updateAccountFromDto(any(AccountCreateDTO.class), any(Account.class));

        AccountCreateDTO accountDTO1 = new AccountCreateDTO("rml@gmail.com", "Deyana MLadenova", "lala123@");
        accountService.update(accountDTO1, 1L);
        assertThat(newAcc.getFullName(), is(accountDTO1.fullName()));
        Mockito.verify(accountRepository).save(account1);
    }

    @Test
    void givenInvalidAccountEmail_whenUpdating_thenEmailException() {
        Account account = new Account("dml@gmail.com", "Ralitsa MLadenova", "lala123@");

        when(accountRepository.findById(anyLong())).thenReturn(Optional.of(account));

        AccountCreateDTO accountDTO = new AccountCreateDTO("rml@gmail.com", "Deyana MLadenova", "lala123@");
        Exception exception = assertThrows(EmailException.class, () -> {
            accountService.update(accountDTO, 1L);
        });
        String expectedMessage = "Email connot be updated!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
    @Test
    void givenValidAccount_whenDeleting_thenOK() {
        Long id = 1L;

        when(accountRepository.existsById(id)).thenReturn(true);

        doNothing().when(accountRepository).deleteById(anyLong());

        accountService.delete(id);
        Mockito.verify(accountRepository).deleteById(id);
    }

    @Test
    void givenInvalidAccount_whenDeleting_thenException() {
        Long id = 1L;

        when(accountRepository.existsById(id)).thenReturn(false);

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            accountService.delete(id);
        });
        String expectedMessage = "Account not found!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }


}
