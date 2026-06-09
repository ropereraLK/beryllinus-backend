package com.beryllinus.backend.enumuration;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Arrays;

public enum RoomClassType {

    EXECUTIVE,
    LUXURY,
    STANDARD;

    @JsonCreator
    public static RoomClassType from(String value) {
        return Arrays.stream(values())
                .filter(type ->
                        type.name().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Invalid room class type"));
    }
}
