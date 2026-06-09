package com.beryllinus.backend.contoller;

import com.beryllinus.backend.model.room.Room;
import com.beryllinus.backend.model.room.RoomClassConfig;
import com.beryllinus.backend.service.RoomClassConfigService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/room-class-configs")
public class RoomClassConfigController {

    private final RoomClassConfigService roomClassConfigService;

    public RoomClassConfigController(
            RoomClassConfigService roomClassConfigService
    ) {
        this.roomClassConfigService = roomClassConfigService;
    }

    /**
     * Get all configs.
     */
    @GetMapping
    public ResponseEntity<List<RoomClassConfig>> getAll() {

        return ResponseEntity.ok(
                roomClassConfigService.getAll()
        );
    }

    /**
     * Get config by id.
     */
    @GetMapping("/{id}")
    public ResponseEntity<RoomClassConfig> getById(
            @PathVariable Integer id
    ) {

        return ResponseEntity.ok(
                roomClassConfigService.getById(id)
        );
    }

    /**
     * Create seasonal pricing/configuration.
     */
    @PostMapping
    public ResponseEntity<RoomClassConfig> create(
            @RequestBody RoomClassConfig request
    ) {

        return ResponseEntity.ok(
                roomClassConfigService.create(request)
        );
    }

    /**
     * Update seasonal pricing/configuration.
     */
    @PutMapping("/{id}")
    public ResponseEntity<RoomClassConfig> update(
            @PathVariable Integer id,
            @RequestBody RoomClassConfig request
    ) {

        return ResponseEntity.ok(
                roomClassConfigService.update(id, request)
        );
    }
}