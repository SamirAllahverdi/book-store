package com.example.exception;


public class UserNotFoundException extends NotFoundException {

    public UserNotFoundException() {
        super("User was not found");
    }

    public UserNotFoundException(Object param) {
        super(String.format("User with '%s' was not found", param));
    }
}
