package com.sbrf.reboot.model;

import com.sbrf.reboot.ref.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@Builder
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Customer {
    private final String name;
    private int age;
    private Set<Account> accounts;
}