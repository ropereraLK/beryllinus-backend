package com.beryllinus.backend.contoller;

import com.beryllinus.backend.model.room.Room;
import com.beryllinus.backend.model.room.RoomConfig;
import com.beryllinus.backend.service.RoomConfigService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/room-configs")
public class RoomConfigController {

    private final RoomConfigService roomConfigService;

    public RoomConfigController(
            RoomConfigService roomConfigService
    ) {
        this.roomConfigService = roomConfigService;
    }

    /**
     * Get all room configs.
     */
    @GetMapping
    public ResponseEntity<List<RoomConfig>> getAll() {

        return ResponseEntity.ok(
                roomConfigService.getAll()
        );
    }

    /**
     * Get room config by id.
     */
    @GetMapping("/{id}")
    public ResponseEntity<RoomConfig> getById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                roomConfigService.getById(id)
        );
    }

    /**
     * Create maintenance/blocking period.
     */
    @PostMapping
    public ResponseEntity<RoomConfig> create(
            @RequestBody RoomConfig request
    ) {

        return ResponseEntity.ok(
                roomConfigService.create(request)
        );
    }

    /**
     * Update maintenance/blocking period.
     */
    @PutMapping("/{id}")
    public ResponseEntity<RoomConfig> update(
            @PathVariable Long id,
            @RequestBody RoomConfig request
    ) {

        return ResponseEntity.ok(
                roomConfigService.update(id, request)
        );
    }
}