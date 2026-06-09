package com.beryllinus.backend.contoller;

import com.beryllinus.backend.dto.request.BookingPrecheckRequest;
import com.beryllinus.backend.mapper.BookingMapper;
import com.beryllinus.backend.model.room.RoomConfig;
import com.beryllinus.backend.repository.RoomConfigRepository;
import com.beryllinus.backend.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/api/v1/bookings")
public class BookingController {

    private final BookingService bookingService;
    private final BookingMapper bookingMapper;

    @Autowired
    private RoomConfigRepository roomConfigRepository;

    public BookingController(BookingService bookingService, BookingMapper bookingMapper) {
        this.bookingService = bookingService;
        this.bookingMapper = bookingMapper;
    }

    //TODO
    @GetMapping
    public ResponseEntity<String> testBooking() {

        List<RoomConfig> roomConfigList = roomConfigRepository.findRoomsConfigByRoomIdAndDateAndIsActive(2, LocalDate.now().plusYears(1));

        return ResponseEntity.ok("Hi");
        //return ResponseEntity.ok(bookingService.createTestBooking(bookingName));
    }

    @PostMapping("/precheck")
    public void preCheckBooking(@Valid @RequestBody BookingPrecheckRequest bookingPrecheckRequest) {
        bookingService.getPreCheckBooking(bookingMapper.validateBookingPrecheckRequest(bookingPrecheckRequest));
    }


}
