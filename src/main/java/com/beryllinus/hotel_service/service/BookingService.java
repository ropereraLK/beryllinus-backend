package com.beryllinus.hotel_service.service;

import com.beryllinus.hotel_service.model.Booking;
import com.beryllinus.hotel_service.repository.BookingRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final Booking booking;

    public BookingService(BookingRepository bookingRepository, Booking booking) {
        this.bookingRepository = bookingRepository;
        this.booking = booking;
    }


    public String createTestBooking(final String testBooking) {

        if (testBooking == null || testBooking.isBlank()) {
            throw new IllegalArgumentException("Not Valid");
        }

        UUID uuid = UUID.randomUUID();

        booking.setId(uuid.toString());
        booking.setName(testBooking);
        bookingRepository.save(booking);
        return "Completed";

    }
}
