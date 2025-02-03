package ru.itmo.transactioins;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Represents the transaction history of an account.
 */
public class TransactionHistory {
    private final Deque<Transaction> history;

    public TransactionHistory() {
        history = new ArrayDeque<>();
    }

    /**
     * Executes the given transaction and adds it to the history.
     *
     * @param command The transaction to execute.
     */
    public void executeCommand(Transaction command) throws Exception {
        command.execute();
        history.push(command);
    }

    /**
     * Adds a transaction to the history without executing it.
     *
     * @param command The transaction to add.
     */
    public void addTransaction(Transaction command) {
        history.add(command);
    }

    /**
     * Undoes the last completed transaction.
     * If there are no completed transactions, prints a message.
     */
    public void undoLastTransaction() throws Exception {
        if (!history.isEmpty()) {
            Transaction lastCommand = history.pop();
            lastCommand.cancel();
        } else {
            System.out.println("There are no completed transactions to cancel.");
        }
    }
}
