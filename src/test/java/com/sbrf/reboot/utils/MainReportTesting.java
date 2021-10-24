package com.sbrf.reboot.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.sbrf.reboot.ref.Account;
import com.sbrf.reboot.model.Customer;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


class MainReportTesting {

    Set<Customer> customers;
    BigDecimal result;

    @BeforeEach
    void setUp() {
        customers = new HashSet<Customer>() {{
            add(Customer.builder().name("customer1").age(16).accounts(new HashSet<Account>() {{   add(Account.builder().number("A1").createDate(LocalDate.of(2021, 7, 15)).balance(new BigDecimal("100000")).build());
            }}).build());
            add(Customer.builder().name("customer2").age(22).accounts(new HashSet<Account>() {{
                add(Account.builder().number("A2").createDate(LocalDate.of(2021, 6, 15)).balance(new BigDecimal("10000")).build());
                add(Account.builder().number("A3").createDate(LocalDate.of(2021, 7,  5)).balance(new BigDecimal("1000")).build());
            }}).build());
            add(Customer.builder().name("customer3").age(26).accounts(new HashSet<Account>() {{
                add(Account.builder().number("A4").createDate(LocalDate.of(2021, 7,  5)).balance(new BigDecimal("100")).build());
                add(Account.builder().number("A5").createDate(LocalDate.of(2021, 7, 25)).balance(new BigDecimal("10")).build());
            }}).build());
            add(Customer.builder().name("customer4").age(36).accounts(new HashSet<Account>() {{
                add(Account.builder().number("A6").createDate(LocalDate.of(2021, 7, 15)).balance(new BigDecimal("1")).build());
            }}).build());
        }};
        result = new BigDecimal("001110");
    }

    @SneakyThrows
    @Test
    void getTotals() {
        assertEquals(result, MainReport.getTotals(customers.stream()));
    }

    @SneakyThrows
    @Test
    void getTotalsWithCompletableFuture() {
        assertEquals(result, MainReport.getTotalsWithCompletableFuture(customers.stream()));
    }

    @SneakyThrows
    @Test
    void getTotalsWithReact(){
        assertEquals(result, MainReport.getTotalsWithReact(customers.stream()));
    }
}


