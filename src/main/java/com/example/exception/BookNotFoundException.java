package com.example.exception;

public class BookNotFoundException extends NotFoundException {

    public BookNotFoundException() {
        super("Book not found");
    }

    public BookNotFoundException(Object param) {
        super(String.format("Book with '%s' was not found", param));
    }
}
