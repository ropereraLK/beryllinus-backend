package com.beryllinus.backend.contoller;

import com.beryllinus.backend.model.room.RoomClass;
import com.beryllinus.backend.service.RoomClassService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/room-classes")
public class RoomClassController {

    private final RoomClassService roomClassService;

    public RoomClassController(
            RoomClassService roomClassService
    ) {
        this.roomClassService = roomClassService;
    }

    /**
     * Get all active room classes.
     */
    @GetMapping
    public ResponseEntity<List<RoomClass>> getAllRoomClasses() {

        return ResponseEntity.ok(
                roomClassService.getAllActiveRoomClasses()
        );
    }

    /**
     * Get room class by id.
     */
    @GetMapping("/{id}")
    public ResponseEntity<RoomClass> getRoomClass(
            @PathVariable Integer id
    ) {

        return ResponseEntity.ok(
                roomClassService.getRoomClassById(id)
        );
    }

}