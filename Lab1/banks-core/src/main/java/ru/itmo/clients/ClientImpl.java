package ru.itmo.clients;

import ru.itmo.accounts.Account;
import ru.itmo.accounts.AccountImpl;
import ru.itmo.banks.Bank;
import ru.itmo.exceptions.FallingTransactionException;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a client of the bank.
 */
public class ClientImpl implements Client {
    private String firstName;
    private String surname;
    private String address;
    private String passportNumber;
    private final List<Account> accounts;
    private boolean isSuspicious;

    public ClientImpl(String name, String surname) {
        this.firstName = name;
        this.surname = surname;
        accounts = new ArrayList<>();
        isSuspicious = false;
        address = "";
        passportNumber = "";
    }

    /**
     * Sets the address for the client.
     *
     * @param address The address to set.
     */
    public void setAddress(String address) {
        this.address = address;
        if (this.address != null && this.passportNumber != null) {
            this.isSuspicious = false;
        }
    }

    /**
     * Sets the passport number for the client.
     *
     * @param passportNumber The passport number to set.
     */
    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
        if (this.address != null && this.passportNumber != null) {
            this.isSuspicious = false;
        }
    }

    /**
     * Sets the first name for the client.
     *
     * @param firstName The passport number to set.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Sets the surname for the client.
     *
     * @param surname The passport number to set.
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Adds an account to the client's list of accounts.
     *
     * @param account The account to add.
     */
    @Override
    public void addAccount(Account account) {
        accounts.add(account);
    }

    /**
     * Sets the account for the client in the specified bank.
     *
     * @param bank    The bank where the account will be set.
     * @param account The account to set.
     */
    @Override
    public void setAccount(Bank bank, Account account) {
        bank.addAccountForClient(account, this);
    }

    /**
     * Gets the list of accounts associated with the client.
     *
     * @return The list of accounts.
     */
    @Override
    public List<Account> getAccounts() {
        return accounts;
    }

    /**
     * Withdraws the specified amount from the account associated with the client.
     *
     * @param amount  The amount to withdraw.
     * @param account The account from which to withdraw.
     */
    @Override
    public void withdraw(double amount, Account account) throws Exception {
        if (!isSuspicious) {
            account.withdraw(amount);
        } else {
            throw new Exception("The operation is not available for a doubtful account.");
        }
    }

    /**
     * Deposits the specified amount into the account associated with the client.
     *
     * @param amount  The amount to deposit.
     * @param account The account into which to deposit.
     */
    @Override
    public void deposit(double amount, Account account) {
        account.deposit(amount);
    }

    /**
     * Transfers the specified amount from one account to another.
     *
     * @param amount  The amount to transfer.
     * @param account The account from which to transfer.
     */
    @Override
    public void transfer(double amount, Account account) throws Exception {
        if (!isSuspicious) {
            account.transfer(account, amount);
        } else {
            throw new FallingTransactionException("The operation is not available for a doubtful account.");
        }
    }

    /**
     * Updates the client's information and marks the client as suspicious if necessary.
     */
    @Override
    public void update() throws Exception {
        if (firstName == null || firstName.isEmpty() || surname == null || surname.isEmpty()) {
            markAsSuspicious();
            throw new Exception("Error: the client's first or last name is not specified.");
        } else {
            throw new Exception("The client's information has been successfully updated.");
        }
    }

    /**
     * Marks the client as suspicious.
     */
    public void markAsSuspicious() {
        this.isSuspicious = true;
    }

    /**
     * Checks if the client is marked as suspicious.
     *
     * @return True if the client is suspicious, false otherwise.
     */
    @Override
    public boolean isSuspicious() {
        return isSuspicious;
    }
}