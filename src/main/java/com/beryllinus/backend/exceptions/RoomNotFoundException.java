package com.beryllinus.backend.exceptions;

public class RoomNotFoundException extends RuntimeException {

    public RoomNotFoundException(String message) {
        super(message);
    }

    public RoomNotFoundException() {

    }
}
