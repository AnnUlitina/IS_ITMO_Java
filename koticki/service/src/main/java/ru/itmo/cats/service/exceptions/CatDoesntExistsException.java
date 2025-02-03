package ru.itmo.cats.service.exceptions;

public class CatDoesntExistsException extends Exception{

    public CatDoesntExistsException(String catName) {
        super("there is no cat with name" + catName);
    }
    public CatDoesntExistsException(int catId) {
        super("there is no cat with id" + catId);
    }
}