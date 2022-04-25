package com.example.exception;


import lombok.Getter;

@Getter
public class InvalidConfirmationKeyException extends RuntimeException {

    private final String message;

    public InvalidConfirmationKeyException(Object param) {
        this.message = String.format("The confirmation key of '%s' is invalid", param);
    }

}
