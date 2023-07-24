package com.bookstore.demo.domain.roles.entity;

import com.bookstore.demo.domain.accounts.entity.Account;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Column(unique=true)
    private String name;

    @OneToMany(mappedBy = "role")
    private List<Account> accounts = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> account) {
        this.accounts = account;
    }

    public void addAccount(Account account) {
        this.accounts.add(account);
        account.setRole(this);
    }

    public void deleteAccount(Account account) {
        this.accounts.remove(account);
        account.setRole(null);
    }

}
