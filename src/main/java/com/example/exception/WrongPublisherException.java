package com.example.exception;

import lombok.Getter;

@Getter
public class WrongPublisherException extends RuntimeException {

    private final String message;

    public WrongPublisherException() {
        this.message = "Only book publisher can edit the book";
    }
}
