package com.beryllinus.backend.exceptions;

public class RoomConfigNotFoundException extends RuntimeException {
    public RoomConfigNotFoundException(String message) {
        super(message);
    }
}
