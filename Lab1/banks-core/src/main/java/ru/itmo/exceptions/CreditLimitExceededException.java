package ru.itmo.exceptions;

public class CreditLimitExceededException extends Exception {
    public CreditLimitExceededException(String errorMessage) {
        super(errorMessage);
    }
}
