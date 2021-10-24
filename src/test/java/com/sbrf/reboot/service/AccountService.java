package com.sbrf.reboot.service;

import com.sbrf.reboot.dto.Account;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Data
public class AccountService {
    private AccountRepository accountRepository;
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }
    public boolean isAccountExist(long accountId, Account account) {
        return accountRepository.getAllAccountsByClientId(accountId).contains(account);
    }

    public Account getMaxAccountBalance(long accountId) throws Exception {
        Stream<Account> stream =  accountRepository.getAllAccountsByClientId(accountId).stream();
        return stream.max(Comparator.comparing(acc -> acc.getBalance())).orElseThrow(Exception::new);
    }

    public Set<Account> getAllAccountsByDateMoreThen(long accountId, LocalDate date) {
        Stream<Account> stream = accountRepository.getAllAccountsByClientId(accountId).stream();
        return stream.filter(account -> {
            boolean b = account.getCreateDate().isAfter(date) || account.getCreateDate().isEqual(date);
            return b;
        }).collect(Collectors.toSet());
    }

    public BigDecimal getAllAccountsBalanceToDollarSum(long accountId, BigDecimal ratesDollar) throws Exception {
        Stream<Account> stream = accountRepository.getAllAccountsByClientId(accountId).stream();
        return stream.map(acc -> acc.getBalance().divide(ratesDollar)).reduce((it1, it2) -> it1.add(it2)).orElseThrow(Exception::new);
    }
}