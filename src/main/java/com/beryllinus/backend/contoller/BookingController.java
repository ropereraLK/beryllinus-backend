package com.beryllinus.backend.contoller;

import com.beryllinus.backend.dto.request.BookingPrecheckRequest;
import com.beryllinus.backend.dto.response.BookingPrecheckResponse;
import com.beryllinus.backend.mapper.BookingMapper;
import com.beryllinus.backend.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/v1/bookings")
public class BookingController {

    private final BookingService bookingService;
    private final BookingMapper bookingMapper;

    public BookingController(BookingService bookingService, BookingMapper bookingMapper) {
        this.bookingService = bookingService;
        this.bookingMapper = bookingMapper;
    }

    @PostMapping("/precheck")
    public ResponseEntity<BookingPrecheckResponse> preCheckBooking(@Valid @RequestBody BookingPrecheckRequest bookingPrecheckRequest) {
        return ResponseEntity.ok(bookingService.getPreCheckBooking(bookingMapper.validateBookingPrecheckRequest(bookingPrecheckRequest)));
    }


}
