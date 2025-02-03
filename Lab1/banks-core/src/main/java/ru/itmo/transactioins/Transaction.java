package ru.itmo.transactioins;

public interface Transaction {
    void execute() throws Exception;
    void cancel() throws Exception;
}
