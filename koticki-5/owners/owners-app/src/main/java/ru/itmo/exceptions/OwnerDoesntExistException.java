package ru.itmo.exceptions;

public class OwnerDoesntExistException extends Exception {
    public OwnerDoesntExistException(Long personId) {

        super("Person not found with ID: " + personId);
    }

    public OwnerDoesntExistException(String  name) {

        super("Person not found with name: " + name);
    }
}
