package ru.itmo.banks;

import ru.itmo.accounts.Account;
import ru.itmo.accounts.AccountImpl;
import ru.itmo.clients.Client;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a central bank that observes and manages other banks.
 */
public class CentralBankImpl implements CentralBank {
    private List<Bank> observers;

    public CentralBankImpl() {
        observers = new ArrayList<>();
    }

    /**
     * Adds a bank as an observer to the central bank.
     *
     * @param observer The bank to add as an observer.
     */
    @Override
    public void addObserver(Bank observer) {
        observers.add(observer);
    }

    /**
     * Removes a bank as an observer from the central bank.
     *
     * @param observer The bank to remove as an observer.
     */
    @Override
    public void removeObserver(Bank observer) {
        observers.remove(observer);
    }

    /**
     * Notifies all observer banks of an update.
     */
    @Override
    public void notifyObservers() {
        for (Bank observer : observers) {
            observer.update();
        }
    }

    /**
     * Creates a new bank and adds it to the central bank as an observer.
     *
     * @return The newly created bank.
     */
    public Bank createBank() {
        Bank newBank = new BankImpl();
        observers.add(newBank);
        return newBank;
    }

    public void makeInterbankTransfer(double amount, Bank senderBank, Bank receiverBank, Client client)
            throws Exception {
        List<Account> clientAccounts = senderBank.getClientAccounts(client);
        if (clientAccounts.isEmpty()) {
            throw new Exception("Client has no accounts in the sender bank.");
        }

        Account senderAccount = clientAccounts.get(0);

        if (senderAccount.getBalance() < amount) {
            throw new Exception("Insufficient funds for the transfer.");
        }

        List<Account> receiverAccounts = receiverBank.getClientAccounts(client);
        if (receiverAccounts.isEmpty()) {
            throw new Exception("Client has no accounts in the receiver bank.");
        }

        Account receiverAccount = receiverAccounts.get(0);
        if (senderBank == receiverBank) {
            senderAccount.transfer(receiverAccount, amount);
        } else {
            List<Account> receiverBankAccounts = receiverBank.getClientAccounts(client);
            if (receiverBankAccounts.isEmpty()) {
                throw new Exception("Receiver bank doesn't have an account for the client.");
            }
            Account receiverBankAccount = receiverBankAccounts.get(0);
            senderAccount.transfer(receiverBankAccount, amount);
        }
    }
}