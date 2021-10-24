package com.sbrf.reboot.utils;

import com.sbrf.reboot.dto.Account;
import com.sbrf.reboot.model.Customer;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainReport {

    public static BigDecimal getTotals(Stream<Customer> customers) {
        return customers
                .filter(customer -> 18 < customer.getAge() && customer.getAge() < 30)
                .flatMap(customer -> customer.getAccounts().stream())
                .filter(account -> account.getCreateDate().isAfter(LocalDate.of(2021, 7, 1)))
                .filter(account -> account.getCreateDate().isBefore(LocalDate.of(2021, 8, 1)))
                .map(Account::getBalance)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                ;
    }

    public static BigDecimal getTotalsWithCompletableFuture(Stream<Customer> customers) throws ExecutionException, InterruptedException {
        List<CompletableFuture<BigDecimal>> accountsSumFutures = customers
                .filter(customer -> 18 < customer.getAge() && customer.getAge() < 30)
                .map(customer -> CompletableFuture.supplyAsync(() ->
                        customer.getAccounts().stream()
                                .filter(account -> account.getCreateDate().isAfter(LocalDate.of(2021, 7, 1)))
                                .filter(account -> account.getCreateDate().isBefore(LocalDate.of(2021, 8, 1)))
                                .map(Account::getBalance)
                                .reduce(BigDecimal.ZERO, BigDecimal::add)
                ))
                .collect(Collectors.toList());

        CompletableFuture<Void> allFutures = CompletableFuture.allOf(
                accountsSumFutures.toArray(new CompletableFuture[0])
        );

        CompletableFuture<List<BigDecimal>> allAccountSumFuture = allFutures.thenApply(v ->
                accountsSumFutures.stream()
                        .map(CompletableFuture::join)
                        .collect(Collectors.toList())
        );

        CompletableFuture<BigDecimal> sumFuture = allAccountSumFuture.thenApply(accountsSum ->
                accountsSum.stream().reduce(BigDecimal.ZERO, BigDecimal::add)
        );

        return sumFuture.get();
    }

    public static BigDecimal getTotalsWithReact(Stream<Customer> customers) throws ExecutionException, InterruptedException {
        return Flux
                .fromStream(customers)
                .filter(customer -> 18 < customer.getAge() && customer.getAge() < 30)
                .parallel()
                .runOn(Schedulers.newParallel("sp", Runtime.getRuntime().availableProcessors()))
                .flatMap(customer -> Flux.fromStream(customer.getAccounts().stream()))
                .filter(account -> account.getCreateDate().isAfter(LocalDate.of(2021, 7, 1)))
                .filter(account -> account.getCreateDate().isBefore(LocalDate.of(2021, 8, 1)))
                .map(Account::getBalance)
                .sequential()
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .toFuture()
                .get()
                ;
    }
}