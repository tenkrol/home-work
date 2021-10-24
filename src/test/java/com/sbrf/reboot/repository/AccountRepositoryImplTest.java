package com.sbrf.reboot.repository;


import com.sbrf.reboot.dto.Account;
import com.sbrf.reboot.service.AccountRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
class AccountRepositoryImplTest {
    AccountRepository accountRepository;
    @Test
    void onlyPersonalAccounts() throws IOException {
        accountRepository = new AccountRepositoryImpl("src/main/resources/Accounts.txt");
        Set<Account> allAccountsByClientId = accountRepository.getAllAccountsByClientId(1);
        ArrayList<Account> strings = new ArrayList<Account>() {{
            add(new Account("2-ACCNUM"));
            add(new Account("1-ACCNUM"));