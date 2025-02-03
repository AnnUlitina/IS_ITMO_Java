package ru.itmo.banks;

import ru.itmo.accounts.Account;
import ru.itmo.accounts.AccountImpl;
import ru.itmo.accounts.DepositAccountImpl;
import ru.itmo.clients.Client;
import ru.itmo.exceptions.FallingTransactionException;
import ru.itmo.transactioins.Transaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a bank entity.
 */
public class BankImpl implements Bank {
    private final List<Client> clients;
    private final Map<String, Transaction> transactions;
    private List<Transaction> mementos;
    private List<Account> accounts;
    private double interestRate;
    private double commission;
    private double transferLimit;

    public BankImpl() {
        clients = new ArrayList<>();
        mementos = new ArrayList<>();
        accounts = new ArrayList<>();
        transactions = new HashMap<>();
        interestRate = 0.0;
        commission = 0.0;
        transferLimit = 0.0;
    }

    /**
     * Updates the bank by withdrawing commission and accruing interest for all accounts.
     */
    @Override
    public void update() {
        for (Account account : accounts) {
            account.withdrawCommission();
            account.accrueInterest(account);
        }
    }

    /**
     * Adds an account to the bank.
     *
     * @param account The account to add.
     */
    public void addAccount(Account account) {
        accounts.add(account);
    }

    /**
     * Removes an account from the bank.
     *
     * @param account The account to remove.
     */
    public void removeAccount(Account account) {
        accounts.remove(account);
    }

    /**
     * Gets a list of all accounts in the bank.
     *
     * @return The list of accounts.
     */
    public List<Account> getAccounts() {
        return accounts;
    }

    /**
     * Adds an account for a client.
     *
     * @param account The account to add.
     * @param client  The client to add the account for.
     */
    @Override
    public void addAccountForClient(Account account, Client client) {
        if (!accounts.contains(account)) {
            if (client.isSuspicious()) {
                changeLimitForTransfer(transferLimit);
            }
            client.addAccount(account);
            this.addAccount(account);
        }
    }

    /**
     * Sets the commission rate for transactions.
     *
     * @param commission The commission rate to set.
     */
    public void setCommission(double commission) {
        this.commission = commission;
    }

    /**
     * Gets the commission rate for transactions.
     *
     * @return The commission rate.
     */
    public double getCommission() {
        return commission;
    }

    /**
     * Changes the transfer limit for transactions.
     *
     * @param transferLimit The new transfer limit to set.
     */
    public void changeLimitForTransfer(double transferLimit) {
        this.transferLimit = transferLimit;
    }

    /**
     * Changes the interest rate for accounts.
     *
     * @param interestRate The new interest rate to set.
     */
    public void changeInterestRateForTransfer(double interestRate) {
        this.interestRate = interestRate;
    }

    /**
     * Gets the interest rate for accounts.
     *
     * @return The interest rate.
     */
    public double getInterestRate() {
        return interestRate;
    }

    /**
     * Cancels a transaction with the specified ID.
     *
     * @param transactionId The ID of the transaction to cancel.
     * @param client        The client associated with the transaction.
     */
    public void cancelTransaction(String transactionId, Client client) throws Exception {
        if (transactions.containsKey(transactionId)) {
            Transaction transaction = transactions.get(transactionId);
            transaction.cancel();
            transactions.remove(transactionId);
        } else {
            throw new FallingTransactionException("The transaction with the specified ID was not found.");
        }
    }

    /**
     * Adds an observer client to the bank.
     *
     * @param observer The observer client to add.
     */
    public void addObserverClient(Client observer) {
        clients.add(observer);
    }

    /**
     * Removes an observer client from the bank.
     *
     * @param observer The observer client to remove.
     */
    public void removeObserverClient(Client observer) {
        clients.remove(observer);
    }

    /**
     * Notifies all observer clients of an update.
     */
    public void notifyObserverClients() {
        update();
    }

    /**
     * Accelerates time by the specified number of months for the specified account.
     *
     * @param months  The number of months to accelerate time by.
     * @param account The account to accelerate time for.
     * @return The updated account after accelerating time.
     */
    public Account timeAcceleration(int months, Account account) {
        Account tempAccount = account;
        for (int i = 0; i < months; i++) {
            if (tempAccount instanceof DepositAccountImpl) {
                DepositAccountImpl depositAccount = (DepositAccountImpl) tempAccount;
                depositAccount.reduceDepositTerm(1); // Уменьшаем срок депозита на 1 месяц
            } else {
                tempAccount.accrueInterest(tempAccount);
                tempAccount.withdrawCommission();
            }
        }
        return tempAccount;
    }

    /**
     * Get the account list of client.
     *
     * @param client Client, whose accounts need to get.
     * @return Accounts list of client.
     */
    @Override
    public List<Account> getClientAccounts(Client client) {
        List<Account> clientAccounts = new ArrayList<>();
        for (Account account : accounts) {
            if (client.getAccounts().contains(account)) {
                clientAccounts.add(account);
            }
        }
        return clientAccounts;
    }
}