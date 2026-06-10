package com.beryllinus.backend.contoller;

import com.beryllinus.backend.dto.request.BookingPrecheckRequest;
import com.beryllinus.backend.dto.request.BookingRequest;
import com.beryllinus.backend.dto.response.BookingPrecheckResponse;
import com.beryllinus.backend.dto.response.BookingResponse;
import com.beryllinus.backend.mapper.BookingMapper;
import com.beryllinus.backend.service.BookingService;
import com.beryllinus.backend.validator.BookingValidator;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/bookings")
public class BookingController {

    private final BookingService bookingService;
    private final BookingMapper bookingMapper;
    private final BookingValidator bookingValidator;

    @PostMapping("/precheck")
    public ResponseEntity<BookingPrecheckResponse> preCheckBooking(
            @Valid @RequestBody BookingPrecheckRequest bookingPrecheckRequest) {
        return ResponseEntity.ok(
                bookingService.getPreCheckBooking(
                        bookingMapper.validateBookingPrecheckRequest(bookingPrecheckRequest)));
    }

    @PostMapping("/temporary")
    public ResponseEntity<BookingResponse> createTemporaryBooking(
            @Valid @RequestBody BookingRequest request) {

        bookingValidator.validate(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(bookingService.createTemporaryBooking(request));
    }

}
