package com.beryllinus.backend.exceptions;

public class RequestValidationException extends RuntimeException {

    public RequestValidationException(String message) {
        super(message);
    }

    public RequestValidationException() {

    }
}