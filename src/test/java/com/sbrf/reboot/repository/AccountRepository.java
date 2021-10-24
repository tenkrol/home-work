package com.sbrf.reboot.repository;

import com.sbrf.reboot.exception.AccountException;
import com.sbrf.reboot.model.Account;

import java.util.Set;

public interface AccountRepository {


    Set<Account> getAllAccountsByClientId(long clientId) throws AccountException;

    Account setAccountNumber(long clientId, String number, String newNumber) throws AccountException;
}