package ru.itmo.cats.service.exceptions;

public class OwnerDoesntExistException extends Exception {
    public OwnerDoesntExistException(int  personId) {

        super("Person not found with ID: " + personId);
    }

    public OwnerDoesntExistException(String  name) {

        super("Person not found with name: " + name);
    }
}
