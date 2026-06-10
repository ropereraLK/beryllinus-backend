package com.beryllinus.backend.mapper;

import com.beryllinus.backend.configuration.BookingConfiguration;
import com.beryllinus.backend.dto.request.BookingPrecheckRequest;
import com.beryllinus.backend.dto.response.BookingResponse;
import com.beryllinus.backend.exceptions.RequestValidationException;
import com.beryllinus.backend.model.Booking;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Component
public class BookingMapper {

    private final BookingConfiguration bookingConfiguration;

    public BookingMapper(BookingConfiguration bookingConfiguration) {
        this.bookingConfiguration = bookingConfiguration;
    }

    /**
     * Normalizes and validates the booking precheck request.
     *
     * Rules:
     * - Check-in date is mandatory.
     * - Check-in date cannot be in the past.
     * - Check-in date cannot exceed the booking window.
     * - If check-out date is not provided, assume a one-night stay.
     * - Check-out date must be after check-in date.
     * - Check-out date cannot exceed the booking window.
     * - Stay length cannot exceed configured maximum.
     * - Rooms defaults to 1 when null, zero or negative.
     */
    public BookingPrecheckRequest validateBookingPrecheckRequest(
            BookingPrecheckRequest bookingPrecheckRequest) {

        LocalDate today = LocalDate.now();

        LocalDate checkIn = bookingPrecheckRequest.getCheckIn();
        LocalDate checkOut = bookingPrecheckRequest.getCheckOut();

        // Validate that the check-in date is not in the past.
        if (checkIn.isBefore(today)) {
            throw new RequestValidationException(
                    "Check-in date cannot be in the past");
        }

        // Validate that the check-in date falls within the booking window.
        if (checkIn.isAfter(
                today.plusDays(
                        bookingConfiguration.getMaxSearchDaysAhead()))) {

            throw new RequestValidationException(
                    "Check-in date cannot be more than "
                            + bookingConfiguration.getMaxSearchDaysAhead()
                            + " days in advance");
        }

        // If checkout date is not provided, assume a one-night stay.
        LocalDate effectiveCheckOut =
                checkOut == null
                        ? checkIn.plusDays(1)
                        : checkOut;

        // Validate that checkout occurs after check-in.
        if (!effectiveCheckOut.isAfter(checkIn)) {
            throw new RequestValidationException(
                    "Check-out date must be after check-in date");
        }

        // Validate that checkout date falls within the booking window.
        if (effectiveCheckOut.isAfter(
                today.plusDays(
                        bookingConfiguration.getMaxSearchDaysAhead()))) {

            throw new RequestValidationException(
                    "Check-out date exceeds booking window");
        }

        // Validate maximum allowed stay length.
        long stayLength = ChronoUnit.DAYS.between(
                checkIn,
                effectiveCheckOut);

        if (stayLength > bookingConfiguration.getMaxStayLength()) {
            throw new RequestValidationException(
                    "Maximum stay length is "
                            + bookingConfiguration.getMaxStayLength()
                            + " days");
        }

        // Apply normalized checkout date.
        bookingPrecheckRequest.setCheckOut(
                effectiveCheckOut);

        // Default room count to 1 when null, zero or negative.
        Integer rooms = bookingPrecheckRequest.getRooms();

        if (rooms == null || rooms <= 0) {
            rooms = 1;
        }

        bookingPrecheckRequest.setRooms(rooms);

        return bookingPrecheckRequest;
    }

    public BookingResponse toResponse(Booking booking) {

        return new BookingResponse(
                booking.getId(),
                booking.getRoomClass().getRoomClassType(),
                booking.getCheckIn(),
                booking.getCheckOut(),
                booking.getRoomsBooked(),
                booking.getStatus(),
                booking.isInternationalBooking(),
                booking.getPricePerNight(),
                booking.getTotalAmount(),
                booking.getCurrency(),
                booking.getSpecialRequests(),
                booking.getExpiresAt()
        );
    }
}