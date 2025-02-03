package ru.itmo.accounts;

public interface Account {
    void deposit(double amount);
    void withdraw(double amount) throws Exception;
    double getBalance();
    void transfer(Account recipient, double amount) throws Exception;
    void calculateInterest() throws Exception;
    void accrueInterest(Account account);
    void withdrawCommission();
}
