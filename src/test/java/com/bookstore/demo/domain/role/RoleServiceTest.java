package com.bookstore.demo.domain.role;

import com.bookstore.demo.domain.accounts.entity.Account;
import com.bookstore.demo.domain.roles.entity.Role;
import com.bookstore.demo.domain.roles.model.RoleGetDTO;
import com.bookstore.demo.domain.roles.repository.RoleRepository;
import com.bookstore.demo.domain.roles.service.RoleServiceImpl;
import com.bookstore.demo.exceptions.EntityNotFoundException;
import com.bookstore.demo.infrastructure.mapper.RoleMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class RoleServiceTest {
    @Mock
    private RoleRepository roleRepository;

    @Mock
    private RoleMapper roleMapper;

    @InjectMocks
    private RoleServiceImpl roleService;

    private Role role;
    private Account account;
    private RoleGetDTO roleDTO;

    @BeforeEach
    void Init() {
        role = new Role();
        role.setId(1L);
        role.setName("ADMIN");
        account = new Account("rml@gmail.com", "Ralitsa MLadenova", "lala123@", role);
        account.setId(1L);
        role.addAccount(account);
        List<Account> accountList = new ArrayList<>();
        accountList.add(account);
        roleDTO = new RoleGetDTO("ADMIN", accountList);
    }
    @Test
    void givenValidID_whenGet_thenOK() {
        Long id = 1L;

        when(roleRepository.findById(anyLong())).thenReturn(Optional.of(role));

        when(roleMapper.roleToRoleGetDTO(any(Role.class))).thenReturn(roleDTO);

        RoleGetDTO found = roleService.getById(id);
        assertThat(found.name(), is(role.getName()));
        verify(roleRepository).findById(id);
    }
    @Test
    void givenInvalidID_whenGet_thenOK() {
        Long id = 10L;

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            roleService.getById(id);
        });

        String expectedMessage = "Role not found!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
    @Test
    void givenValidID_whenGetAll_thenOK() {
        Pageable pageable = PageRequest.of(0, 20);
        List<Role> roleList = new ArrayList<>();
        roleList.add(role);
        Page<Role> page = new PageImpl<>(roleList);

        when(roleRepository.findAll(any(Pageable.class))).thenReturn(page);

        when(roleMapper.roleToRoleGetDTO(any(Role.class))).thenReturn(roleDTO);

        Page<RoleGetDTO> res = roleService.getAll(pageable);
        List<RoleGetDTO> resList = res.getContent();
        assertThat(roleList.get(0).getName(), is(resList.get(0).name()));
        assertThat(roleList.get(0).getAccounts().get(0).getId(), is(resList.get(0).accounts().get(0).getId()));
        assertThat(roleList.get(0).getAccounts().get(0).getFullName(), is(resList.get(0).accounts().get(0).getFullName()));
        assertThat(roleList.get(0).getAccounts().get(0).getEmail(), is(resList.get(0).accounts().get(0).getEmail()));
        assertThat(roleList.get(0).getAccounts().get(0).getRole(), is(resList.get(0).accounts().get(0).getRole()));
    }

}
