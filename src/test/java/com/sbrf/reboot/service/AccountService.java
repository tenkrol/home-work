package com.sbrf.reboot.service;

import com.sbrf.reboot.model.Account;
import com.sbrf.reboot.repository.AccountRepository;
import lombok.Data;

import java.util.Set;

@Data
public class AccountService {

    private final AccountRepository accountRepository;

    public boolean isAccountExist(long clientId, Account account) {
        Set<Account> accounts = accountRepository.getAllAccountsByClientId(clientId);
        return accounts.contains(account);
    }
}