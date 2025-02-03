package ru.itmo.exceptions;

public class FallingTransactionException extends Exception{
    public FallingTransactionException(String errorMessage) {
        super(errorMessage);
    }
}
