package ru.itmo.transactioins;

public class TransactionService {
    private final TransactionHistory transactionHistory;

    public TransactionService(TransactionHistory transactionHistory) {
        this.transactionHistory = transactionHistory;
    }

    public void addTransaction(Transaction transaction) {
        transactionHistory.addTransaction(transaction);
    }

    public void executeTransaction(Transaction transaction) throws Exception {
        transactionHistory.executeCommand(transaction);
    }

    public void cancelLastTransaction() throws Exception {
        transactionHistory.undoLastTransaction();
    }
}

