package ru.itmo.accounts;

import ru.itmo.exceptions.FallingTransactionException;
import ru.itmo.transactioins.DepositCommandImpl;
import ru.itmo.transactioins.TransactionHistory;
import ru.itmo.transactioins.TransactionService;
import ru.itmo.transactioins.WithdrawCommandImpl;

/**
 * Represents a deposit account.
 */
public class DepositAccountImpl implements Account {
    private double balance;
    private final double interestRate;
    private final double commission;
    private final TransactionHistory transactions;
    private final TransactionService transactionService;
    private int depositTerm;

    public DepositAccountImpl(double initialBalance, double interestRate, int depositTerm) {
        this.balance = initialBalance;
        this.interestRate = interestRate;
        transactions = new TransactionHistory();
        transactionService = new TransactionService(transactions);
        commission = 0.0;
        this.depositTerm = depositTerm;
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
     * Withdraws the specified amount from the account.
     *
     * @param amount The amount to withdraw.
     */
    @Override
    public void withdraw(double amount) throws FallingTransactionException {
        if (depositTerm > 0) {
            throw new FallingTransactionException("It is not allowed to withdraw money before the deposit term expires.");
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
        double monthlyInterest = balance * (interestRate / 1200);
        balance += monthlyInterest;
    }

    /**
     * Transfers the specified amount from this account to the recipient account.
     *
     * @param recipient The recipient account.
     * @param amount    The amount to transfer.
     */
    @Override
    public void transfer(Account recipient, double amount) throws FallingTransactionException {
        throw new FallingTransactionException(
                "It is not allowed to transfer money from the deposit account before the expiration date.");
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
        balance -= commission * balance / 100;
    }

    /**
     * Reduces the remaining deposit term by the specified number of months.
     *
     * @param months The number of months to reduce the deposit term by.
     */
    public void reduceDepositTerm(int months) {
        depositTerm -= months;
    }

    /**
     * Gets the remaining deposit term.
     *
     * @return The remaining deposit term.
     */
    public int getDepositTerm() {
        return depositTerm;
    }
}