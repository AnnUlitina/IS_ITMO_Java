package ru.itmo.clients;

import ru.itmo.accounts.Account;
import ru.itmo.accounts.AccountImpl;

public interface ClientBuilder {
    ClientImpl build();
    ClientBuilderImpl setAddress(String address);
    ClientBuilderImpl setPassportNumber(String passportNumber);
    ClientBuilderImpl setAccount(Account account);
}
