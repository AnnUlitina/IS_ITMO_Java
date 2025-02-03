package ru.itmo.transactioins;

import ru.itmo.accounts.AccountImpl;
import ru.itmo.accounts.AccountMemento;
import ru.itmo.accounts.Account;

/**
 * Command class representing a deposit transaction.
 */
public class DepositCommandImpl implements Transaction {
    private final Account account;
    private final double amount;
    private AccountMemento memento;

    public DepositCommandImpl(Account account, double amount) {
        this.account = account;
        this.amount = amount;
        memento = null;
    }

    /**
     * Executes the deposit transaction.
     * Stores the account state before the deposit in a memento.
     */
    @Override
    public void execute() {
        memento = new AccountMemento(account, account.getBalance());
        account.deposit(amount);
    }

    /**
     * Cancels the deposit transaction by reverting the deposit.
     */
    @Override
    public void cancel() throws Exception {
        account.withdraw(amount);
    }
}
