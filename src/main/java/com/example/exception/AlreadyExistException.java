package com.example.exception;

import lombok.Getter;

@Getter
public class AlreadyExistException extends RuntimeException {

    private final String message;

    public AlreadyExistException() {
        this.message = "Record was already existed";
    }

    public AlreadyExistException(Object param) {
        this.message = String.format("Record with '%s' was already existed", param);
    }


}
