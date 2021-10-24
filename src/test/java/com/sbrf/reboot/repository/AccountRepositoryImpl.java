package com.sbrf.reboot.repository;


import com.sbrf.reboot.dto.Account;
import com.sbrf.reboot.service.AccountRepository;
import lombok.SneakyThrows;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
public class AccountRepositoryImpl implements AccountRepository {
    private String pathFile;
    private String dataFile;
    private Set<Account> accounts = new HashSet<>();
    @SneakyThrows
    public AccountRepositoryImpl(String pathFile){
        this.pathFile = pathFile;
        readFile();
    }
    @Override
    public Set<Account> getAllAccountsByClientId(long accountId) {
        String regex = "}";
        String[] array = this.dataFile.split(regex);
        for (String obj : array){
            if (obj.contains("clientId\":"+accountId)) {
                StringBuilder stringBuilder = new StringBuilder(obj).reverse();
                String reversed =  new StringBuilder(stringBuilder.substring(1 , stringBuilder.indexOf("\"", 1))).reverse().toString();
                this.accounts.add(new Account(reversed));
            }
        }
        return this.accounts;
    }
    public void replaceAccountsByClientId(long accountId, String accountNumber) {
        String regex = "clientId\":"+accountId+",\"number\":\"";
        StringBuilder stringBuilder = new StringBuilder(this.dataFile);
        stringBuilder.replace(this.dataFile.indexOf(regex)+regex.length(),this.dataFile.indexOf("\"", this.dataFile.indexOf(regex)+regex.length()), accountNumber);
        this.dataFile = stringBuilder.toString();
        writeFile();
    }
    private void writeFile(){
        try (FileWriter writer = new FileWriter(this.pathFile, false)){
            writer.append(this.dataFile);
            writer.flush();
        }
        catch (IOException err){
            err.printStackTrace();
        }
    }
    private void readFile() throws FileNotFoundException{
        File file = new File(this.pathFile);
        FileReader fileReader = new FileReader(file);
        String resultLine = "";
        try (BufferedReader reader = new BufferedReader(fileReader)){
            String line = reader.readLine();
            while (line != null){
                resultLine += line;
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.dataFile =  resultLine.replace(" ", "").replace("\\s", "");
    }
}