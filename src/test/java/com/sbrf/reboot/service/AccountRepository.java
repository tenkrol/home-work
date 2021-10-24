package com.sbrf.reboot.service;


import com.sbrf.reboot.dto.Account;

import java.io.FileNotFoundException;

import java.util.HashSet;
import java.util.Set;
public interface AccountRepository {
    Set<Account> getAllAccountsByClientId(long accountId);
}