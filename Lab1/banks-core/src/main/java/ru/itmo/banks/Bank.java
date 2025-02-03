package ru.itmo.banks;

import ru.itmo.accounts.Account;
import ru.itmo.accounts.AccountImpl;
import ru.itmo.clients.Client;

import java.util.List;

public interface Bank {

    void update();
    void addAccountForClient(Account account, Client client);
    List<Account> getClientAccounts(Client client);
}
