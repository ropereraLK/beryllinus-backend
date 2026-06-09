package com.beryllinus.backend.contoller;

import com.beryllinus.backend.model.room.Room;
import com.beryllinus.backend.service.RoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rooms")
public class RoomController {

    private final RoomService roomService;

    public RoomController(
            RoomService roomService
    ) {
        this.roomService = roomService;
    }

    /**
     * Get all active rooms.
     */
    @GetMapping
    public ResponseEntity<List<Room>> getAllRooms() {

        return ResponseEntity.ok(
                roomService.getAllActiveRooms()
        );
    }

    /**
     * Get room by id.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Room> getRoom(
            @PathVariable Integer id
    ) {

        return ResponseEntity.ok(
                roomService.getRoomById(id)
        );
    }

    /**
     * Get rooms belonging to a room class.
     */
    @GetMapping("/room-class/{roomClassId}")
    public ResponseEntity<List<Room>> getRoomsByRoomClass(
            @PathVariable Long roomClassId
    ) {

        return ResponseEntity.ok(
                roomService.getRoomsByRoomClass(roomClassId)
        );
    }
}

