package ru.itmo.clients;

import ru.itmo.accounts.Account;
import ru.itmo.accounts.AccountImpl;
import ru.itmo.banks.Bank;

import java.util.List;

public interface Client {
    void withdraw(double amount, Account account) throws Exception;

    void deposit(double amount, Account account);

    void transfer(double amount, Account account) throws Exception;
    void update() throws Exception;
    List<Account> getAccounts();
    void setAccount(Bank bank, Account account);
    void addAccount(Account account);
    boolean isSuspicious();
}
