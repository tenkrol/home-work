package com.sbrf.reboot.repository;

import com.sbrf.reboot.exception.AccountException;
import com.sbrf.reboot.model.Account;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AccountRepositoryImplTest {

    AccountRepository accountRepository;

    @Test
    void onlyPersonalAccounts() throws AccountException {
        accountRepository = new AccountRepositoryImpl("src/main/resources/Accounts.txt");
        Set<String> allAccountsByClientId = accountRepository.getAllAccountsByClientId(1)
                .stream()
                .map(Account::getNumber)
                .collect(Collectors.toSet());
        ArrayList<String> strings = new ArrayList<String>() {{
            add("2-ACCNUM");
            add("1-ACCNUM");
            add("4-ACC1NUM");
        }};
        assertTrue(strings.containsAll(allAccountsByClientId) && allAccountsByClientId.containsAll(strings));
    }

    @Test
    void successGetAllAccountsByClientId() throws AccountException {
        accountRepository = new AccountRepositoryImpl("src/main/resources/Accounts.txt");

        accountRepository.setAccountNumber(1, "4-ACC1NUM", "5-ACC1NUM");

        Set<String> allAccountsByClientId = accountRepository.getAllAccountsByClientId(1)
                .stream()
                .map(Account::getNumber)
                .collect(Collectors.toSet());
        assertTrue(allAccountsByClientId.contains("5-ACC1NUM"));

        accountRepository.setAccountNumber(1, "5-ACC1NUM", "4-ACC1NUM");
    }

    @Test
    void failGetAllAccountsByClientId() {
        accountRepository = new AccountRepositoryImpl("somePath");
        assertThrows(AccountException.class, () -> accountRepository.getAllAccountsByClientId(1L));
    }
}