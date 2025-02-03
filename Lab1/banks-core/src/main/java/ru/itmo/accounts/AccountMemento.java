package ru.itmo.accounts;

/**
 * Represents a memento for storing the state of an account.
 */
public class AccountMemento {
    private final Account account;
    private final double balance;

    public AccountMemento(Account account, double balance) {
        this.account = account;
        this.balance = balance;
    }

    /**
     * Restores the state of the associated account from the memento.
     */
    public void restore() {
        account.getBalance();
    }

    /**
     * Gets the balance stored in the memento.
     *
     * @return The balance.
     */
    public double getBalance() {
        return balance;
    }
}
