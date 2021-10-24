package com.sbrf.reboot.repository;

import com.sbrf.reboot.exception.AccountException;
import com.sbrf.reboot.model.Account;
import lombok.RequiredArgsConstructor;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class AccountRepositoryImpl implements AccountRepository {
    private final String filePathName;

    /**
     * Read all accounts data from file storage.
     *
     * @return Set of Account
     * @throws AccountException if there are problems when working with the storage.
     * @see Account
     */
    private Set<Account> getAccounts() throws AccountException {
        Set<Account> accounts = new HashSet<>();

        InputStream inputStream;
        Reader inputStreamReader;
        try {
            inputStream = new FileInputStream(filePathName);
            inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new AccountException(e.getMessage(), e.getCause());
        }

        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String accountsData = bufferedReader.lines().collect(Collectors.joining());

        String regex = "\\s(\\d+)[\\n\\s]*,[\\n\\s]*\"[^\"]+\":[\\n\\s]*\"([^\"]+)\"";

        Matcher matcher = Pattern.compile(regex, Pattern.DOTALL).matcher(accountsData);
        while (matcher.find()) {
            accounts.add(new Account(matcher.group(2)).setClientId(Long.parseLong(matcher.group(1))));
        }
        return accounts;
    }

    /**
     * Write all accounts data to file storage.
     *
     * @param accounts Set of Account
     * @throws AccountException if there are problems when working with the storage.
     * @see Account
     */
    private void setAccounts(Set<Account> accounts) throws AccountException {
        OutputStream outputStream;
        try {
            outputStream = new FileOutputStream(filePathName);
            outputStream.write(
                    (
                            "[\n" +
                                    accounts
                                            .stream()
                                            .map(account -> String.format(
                                                    "  {\n" +
                                                            "    \"clientId\": %d,\n" +
                                                            "    \"number\": \"%s\"\n" +
                                                            "  }"
                                                    , account.getClientId(), account.getNumber()))
                                            .collect(Collectors.joining(",\n")) +
                                    "\n]"
                    ).getBytes(StandardCharsets.UTF_8)
            );
        } catch (IOException e) {
            throw new AccountException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public Set<Account> getAllAccountsByClientId(long clientId) throws AccountException {
        return getAccounts()
                .stream()
                .filter(account -> account.getClientId() == clientId)
                .collect(Collectors.toSet());
    }

    @Override
    public Account setAccountNumber(long clientId, String number, String newNumber) throws AccountException {
        Set<Account> accounts = new HashSet<>(getAccounts());

        final Account newAccount = new Account(newNumber).setClientId(clientId);

        accounts = accounts.stream().map(account ->
                (account.getClientId() == clientId && account.getNumber().equals(number)) ? newAccount : account
        ).collect(Collectors.toSet());

        setAccounts(accounts);

        return newAccount;
    }
}