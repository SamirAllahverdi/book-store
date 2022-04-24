package com.example.exception;

import lombok.Getter;


@Getter
public class CustomNotFoundException extends RuntimeException {
    private final String message;

    public CustomNotFoundException() {
        this.message = "Record was not found";
    }

    public CustomNotFoundException(Object param) {
        this.message = String.format("Record with '%s' was not found", param);
    }


}
