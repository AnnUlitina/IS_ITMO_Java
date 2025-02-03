package ru.itmo.service.exceptions;

public class CatDoesntExistsException extends Exception{

    public CatDoesntExistsException(String catName) {
        super("there is no cat with name" + catName);
    }
    public CatDoesntExistsException(Long catId) {
        super("there is no cat with id" + catId);
    }
}