package com.bookstore.demo.exceptions;

public class EntityNotFoundException extends RuntimeException{
    public EntityNotFoundException(String errorMessage) {
        super(errorMessage);
    }

}
