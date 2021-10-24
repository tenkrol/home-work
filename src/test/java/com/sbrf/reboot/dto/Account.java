package com.sbrf.reboot.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.math.BigDecimal;
import java.time.LocalDate;
@Data
public class Account{
    private String accountNumber;
    private long id;
    private LocalDate createDate;
    private BigDecimal balance;
    private long clientId;

    @Builder
    public Account(long id, LocalDate createDate, BigDecimal balance){
        this.id = id;
        this.createDate = createDate;
        this.balance = balance;
    }

    @Builder
    public Account(long id, BigDecimal balance, long clientId){
        this.id = id;
        this.balance = balance;
        this.clientId = clientId;
    }

    @Builder
    public Account(LocalDate createDate, BigDecimal balance, long clientId){
        this.createDate = createDate;
        this.clientId = clientId;
    }

    public Account(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}