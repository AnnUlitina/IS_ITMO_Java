package ru.itmo.accounts;

import ru.itmo.transactioins.Transaction;
import ru.itmo.transactioins.TransactionHistory;
import ru.itmo.transactioins.TransactionService;
import ru.itmo.transactioins.WithdrawCommandImpl;

/**
 * This class represents a bank account.
 */
public class AccountImpl {
    private final TransactionService transactions;
    private final TransactionHistory transactionHistory;
    private Account account;

    public AccountImpl(Account account) {
        this.account= account;
        transactionHistory = new TransactionHistory();
        transactions = new TransactionService(transactionHistory);
    }

    /**
     * Gets the current balance of the account.
     *
     * @return The current balance.
     */
    public double getBalance() {
        return account.getBalance();
    }

    /**
     * Creates a memento representing the current state of the account.
     *
     * @return The memento object.
     */
    public AccountMemento createMemento() {
        return new AccountMemento((Account) this, getBalance());
    }

    /**
     * Restores the account state from a memento.
     *
     * @param memento The memento to restore from.
     */
    public void restore(AccountMemento memento) {
        memento.restore();
    }

    /**
     * Executes a transaction on the account.
     *
     * @param transaction The transaction to execute.
     */
    public void executeTransaction(Transaction transaction) throws Exception {
        transactions.executeTransaction(transaction);
        transaction.execute();
    }

    /**
     * Deposits an amount into the account.
     *
     * @param amount The amount to deposit.
     */
    public void deposit(double amount) {
        account.deposit(amount);
        transactions.addTransaction(new WithdrawCommandImpl(account, amount));
    }

    /**
     * Withdraws an amount from the account.
     *
     * @param amount The amount to withdraw.
     */
    public void withdraw(double amount) throws Exception {
        account.withdraw(amount);
        transactions.addTransaction(new WithdrawCommandImpl(account, amount));
    }

    public void transfer(Account recipient, double amount) throws Exception {
        account.transfer(recipient, amount);
    }

    void calculateInterest() throws Exception {
        account.calculateInterest();
    }

    public void accrueInterest(Account account) {
        this.account.accrueInterest(account);
    }

    public void withdrawCommission() {
        account.withdrawCommission();
    }

    /**
     * Gets the transaction history of the account.
     *
     * @return The transaction history.
     */
    public TransactionHistory getTransactionHistory() {
        return transactionHistory;
    }
}