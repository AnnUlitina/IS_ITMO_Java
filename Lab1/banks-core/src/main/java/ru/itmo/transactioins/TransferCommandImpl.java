package ru.itmo.transactioins;

import ru.itmo.accounts.Account;
import ru.itmo.accounts.AccountImpl;
import ru.itmo.accounts.AccountMemento;

public class TransferCommandImpl implements Transaction {
    private final Account account_from;
    private final Account account_to;
    private final double amount;
    private AccountMemento memento;

    public TransferCommandImpl(Account account_from, Account account_to, double amount) {
        this.account_from = account_from;
        this.account_to = account_to;
        this.amount = amount;
        memento = null;
    }

    /**
     * Executes the transfer transaction.
     * Stores the account state before the transfer in a memento.
     */
    @Override
    public void execute() throws Exception {
        memento = new AccountMemento(account_from, account_from.getBalance());
        account_from.transfer(account_to, amount);
    }

    /**
     * Cancels the transfer transaction by reverting the transfer.
     */
    @Override
    public void cancel() throws Exception {
        account_from.deposit(amount);
        account_to.withdraw(amount);
    }
}
