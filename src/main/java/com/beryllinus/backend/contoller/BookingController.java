package com.beryllinus.backend.contoller;

import com.beryllinus.backend.model.room.RoomConfig;
import com.beryllinus.backend.repository.RoomConfigRepository;
import com.beryllinus.backend.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private BookingService bookingService;

    @Autowired
    private RoomConfigRepository roomConfigRepository;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    //TODO
    @GetMapping
    public ResponseEntity<String> testBooking() {

        List<RoomConfig> roomConfigList = roomConfigRepository.findRoomsConfigByRoomIdAndDateAndIsActive(2, LocalDate.now().plusYears(1));

        return ResponseEntity.ok("Hi");
        //return ResponseEntity.ok(bookingService.createTestBooking(bookingName));
    }


}
