package ru.itmo.accounts;

import ru.itmo.exceptions.FallingTransactionException;
import ru.itmo.transactioins.TransactionHistory;
import ru.itmo.transactioins.TransactionService;
import ru.itmo.transactioins.TransferCommandImpl;
import ru.itmo.transactioins.WithdrawCommandImpl;

/**
 * Represents a debit account.
 */
public class DebitAccountImpl implements Account {
    private double balance;
    private final double interestRate;
    private final double commission;
    private final TransactionHistory transactions;
    private final TransactionService transactionService;

    public DebitAccountImpl(double initialBalance, double interestRate) {
        balance = initialBalance;
        this.interestRate = interestRate;
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
    public void withdraw(double amount) throws FallingTransactionException {
        if (balance < amount) {
            throw new FallingTransactionException("Insufficient funds in the account.");
        } else {
            balance -= amount;
            transactionService.addTransaction(new WithdrawCommandImpl(this, amount));
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
     * Calculates and accrues interest for the account.
     */
    @Override
    public void calculateInterest() {
        double dailyInterest = balance * (interestRate / 36500);
        balance += dailyInterest;
    }

    /**
     * Transfers the specified amount from this account to the recipient account, if possible.
     *
     * @param recipient The recipient account.
     * @param amount    The amount to transfer.
     */
    @Override
    public void transfer(Account recipient, double amount) throws FallingTransactionException {
        double availableFunds = balance;
        double initialBalance = balance;
        if (amount <= availableFunds) {
            try {
                withdraw(amount);
                recipient.deposit(amount);
                transactionService.addTransaction(new TransferCommandImpl(this, recipient, amount));
            } catch (Exception e) {
                balance = initialBalance;
                System.err.println("Transfer failed: " + e.getMessage());
            }
        } else {
            throw new FallingTransactionException("Insufficient funds in the account for transfer.");
        }
    }

    /**
     * Accrues interest for the account.
     *
     * @param account The account to accrue interest for.
     */
    @Override
    public void accrueInterest(Account account) {
        calculateInterest();
    }

    /**
     * Withdraws commission from the account.
     */
    @Override
    public void withdrawCommission() {
        balance -= 0;
    }
}