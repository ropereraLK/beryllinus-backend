package com.beryllinus.backend.service;

import com.beryllinus.backend.exceptions.RoomConfigNotFoundException;
import com.beryllinus.backend.exceptions.RoomNotFoundException;
import com.beryllinus.backend.model.room.Room;
import com.beryllinus.backend.model.room.RoomConfig;
import com.beryllinus.backend.repository.RoomConfigRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomConfigService {

    private final RoomConfigRepository roomConfigRepository;
    private final RoomService roomService;

    public RoomConfigService(
            RoomConfigRepository roomConfigRepository,
            RoomService roomService
    ) {
        this.roomConfigRepository = roomConfigRepository;
        this.roomService = roomService;
    }

    /**
     * Get all RoomConfigs.
     */
    public List<RoomConfig> getAll() {

        return roomConfigRepository.findAll();
    }

    /**
     * Get RoomConfig by id.
     */
    public RoomConfig getById(
            Long id
    ) {

        return roomConfigRepository
                .findById(id)
                .orElseThrow(
                        () -> new RoomConfigNotFoundException(
                                "RoomConfig not found. id=" + id
                        )
                );
    }

    /**
     * Get all configs belonging to a Room.
     */
    public List<RoomConfig> getByRoom(
            Integer roomId
    ) {

        Room room =
                roomService.getRoomById(roomId);

        return roomConfigRepository.findByRoom(room);
    }

    /**
     * Create RoomConfig.
     *
     * Examples:
     * - Maintenance periods
     * - Temporary room closures
     * - Renovations
     * - Manual room blocking
     */
    public RoomConfig create(
            RoomConfig roomConfig
    ) {

        validateRoomExists(
                roomConfig.getRoom()
        );

        return roomConfigRepository.save(
                roomConfig
        );
    }

    /**
     * Update RoomConfig.
     */
    public RoomConfig update(
            Long id,
            RoomConfig request
    ) {

        RoomConfig existing =
                getById(id);

        validateRoomExists(
                request.getRoom()
        );

        existing.setActive(
                request.isActive()
        );

        existing.setStartDate(
                request.getStartDate()
        );

        existing.setEndDate(
                request.getEndDate()
        );

        return roomConfigRepository.save(
                existing
        );
    }

    /**
     * Validate referenced Room exists.
     */
    private void validateRoomExists(
            Room room
    ) {

        if (room == null) {
            throw new RoomNotFoundException(
                    "Room cannot be null"
            );
        }

        roomService.getRoomById(
                room.getId()
        );
    }
}