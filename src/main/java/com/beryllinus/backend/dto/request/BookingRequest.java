package com.beryllinus.backend.dto.request;

import com.beryllinus.backend.enumuration.RoomClassType;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record BookingRequest(

        @NotNull
        RoomClassType roomClassType,

        @NotNull
        @Future
        LocalDate checkIn,

        @NotNull
        @Future
        LocalDate checkOut,

        @NotNull
        @Positive
        Integer roomsBooked,

        boolean internationalBooking,

        String specialRequests
) {
}