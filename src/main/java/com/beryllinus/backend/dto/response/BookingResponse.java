package com.beryllinus.backend.dto.response;

import com.beryllinus.backend.enumuration.BookingStatus;
import com.beryllinus.backend.enumuration.Currency;
import com.beryllinus.backend.enumuration.RoomClassType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

public record BookingResponse(
        UUID bookingId,

        RoomClassType roomClassType,

        LocalDate checkIn,

        LocalDate checkOut,

        Integer roomsBooked,

        BookingStatus status,

        boolean internationalBooking,

        BigDecimal pricePerNight,

        BigDecimal totalAmount,

        Currency currency,

        String specialRequests,

        OffsetDateTime expiresAt) {
}
