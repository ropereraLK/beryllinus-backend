package com.beryllinus.backend.exceptions;

public class RoomClassNotFoundException extends RuntimeException {

    public RoomClassNotFoundException(String message) {
        super(message);
    }
}