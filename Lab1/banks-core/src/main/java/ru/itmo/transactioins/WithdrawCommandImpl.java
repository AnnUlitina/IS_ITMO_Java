package ru.itmo.transactioins;

import ru.itmo.accounts.AccountImpl;
import ru.itmo.accounts.AccountMemento;
import ru.itmo.accounts.Account;

/**
 * Represents a withdrawing command for an account.
 */
public class WithdrawCommandImpl implements Transaction {
    private final Account account;
    private final double amount;
    private AccountMemento memento;

    public WithdrawCommandImpl(Account account, double amount) {
        this.account = account;
        this.amount = amount;
        memento = null;
    }

    /**
     * Executes the withdrawing command, saving the account state before the withdrawal.
     */
    @Override
    public void execute() throws Exception {
        memento = new AccountMemento(account, account.getBalance());
        account.withdraw(amount);
    }

    /**
     * Cancels the withdrawing command by depositing the withdrawn amount back into the account.
     */
    @Override
    public void cancel() {
        account.deposit(amount);
    }
}
