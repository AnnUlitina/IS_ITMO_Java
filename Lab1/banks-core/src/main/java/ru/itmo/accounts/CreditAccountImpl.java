package ru.itmo.accounts;

import ru.itmo.exceptions.CreditLimitExceededException;
import ru.itmo.exceptions.FallingTransactionException;
import ru.itmo.transactioins.TransactionHistory;
import ru.itmo.transactioins.TransactionService;
import ru.itmo.transactioins.TransferCommandImpl;
import ru.itmo.transactioins.WithdrawCommandImpl;

/**
 * Represents a credit account.
 */
public class CreditAccountImpl implements Account {
    private double balance;
    private final double commission;
    private final TransactionHistory transactions;
    private final TransactionService transactionService;

    public CreditAccountImpl(double initialBalance) {
        balance = initialBalance;
        transactions = new TransactionHistory();
        transactionService = new TransactionService(transactions);
        commission = 0.0;
    }

    /**
     * Deposits the specified amount into the account.
     *
     * @param amount The amount to deposit.
     */
    @Override
    public void deposit(double amount) {
        balance += amount;
        transactionService.addTransaction(new WithdrawCommandImpl(this, amount));
    }

    /**
     * Withdraws the specified amount from the account, if possible.
     *
     * @param amount The amount to withdraw.
     */
    @Override
    public void withdraw(double amount) throws Exception {
        double availableFunds = balance;
        if (amount <= availableFunds) {
            balance -= amount;
            transactionService.addTransaction(new WithdrawCommandImpl(this, amount));
        } else {
            throw new CreditLimitExceededException("\"The credit limit has been exceeded.\"");
        }
    }

    /**
     * Gets the current balance of the account.
     *
     * @return The balance.
     */
    @Override
    public double getBalance() {
        return balance;
    }

    /**
     * Calculates interest for the account.
     */
    @Override
    public void calculateInterest() throws Exception {
        throw new Exception("No interest can be credited to the credit account");
    }

    /**
     * Transfers the specified amount from this account to the recipient account, if possible.
     *
     * @param recipient The recipient account.
     * @param amount    The amount to transfer.
     */
    @Override
    public void transfer(Account recipient, double amount) throws FallingTransactionException,
            CreditLimitExceededException {
        double availableFunds = balance;
        double initialBalance = balance;
        if (amount <= availableFunds) {
            try {
                withdraw(amount);
                recipient.deposit(amount);
                transactionService.addTransaction(new TransferCommandImpl(this, recipient, amount));
            } catch (Exception exception) {
                balance = initialBalance;
                throw new FallingTransactionException("Transfer failed: " + exception.getMessage());
            }
        } else {
            String message = "The credit limit for the transfer has been exceeded.";
            throw new CreditLimitExceededException(message);
        }
    }

    /**
     * Accrues interest for the account.
     *
     * @param account The account to accrue interest for.
     */
    @Override
    public void accrueInterest(Account account) {
        return;
    }

    /**
     * Withdraws commission from the account.
     */
    @Override
    public void withdrawCommission() {
        if (balance < 0) {
            balance -= commission * balance / 100;
        }
    }
}
