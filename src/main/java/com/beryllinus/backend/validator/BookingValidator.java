package com.beryllinus.backend.validator;



import com.beryllinus.backend.configuration.BookingConfiguration;
import com.beryllinus.backend.dto.request.BookingRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Component
@RequiredArgsConstructor
public class BookingValidator {

    private final BookingConfiguration bookingConfiguration;

    public void validate(BookingRequest request) {

        validateCheckInDate(request);
        validateCheckOutDate(request);
        validateStayLength(request);
    }

    private void validateCheckInDate(BookingRequest request) {

        LocalDate today = LocalDate.now();

        if (request.checkIn().isBefore(today)) {
            throw new IllegalArgumentException(
                    "Check-in date cannot be in the past");
        }

        if (request.checkIn().isAfter(today.plusDays(363))) {
            throw new IllegalArgumentException(
                    "Check-in date cannot be more than 363 days in advance");
        }
    }

    private void validateCheckOutDate(BookingRequest request) {

        LocalDate today = LocalDate.now();

        if (!request.checkOut().isAfter(request.checkIn())) {
            throw new IllegalArgumentException(
                    "Check-out date must be after check-in date");
        }

        if (request.checkOut().isAfter(today.plusDays(364))) {
            throw new IllegalArgumentException(
                    "Check-out date cannot be more than 364 days in advance");
        }
    }

    private void validateStayLength(BookingRequest request) {

        long stayLength = ChronoUnit.DAYS.between(
                request.checkIn(),
                request.checkOut());

        if (stayLength > bookingConfiguration.getMaxStayLength()) {
            throw new IllegalArgumentException(
                    String.format(
                            "Maximum stay length is %d nights",
                            bookingConfiguration.getMaxStayLength()));
        }
    }
}