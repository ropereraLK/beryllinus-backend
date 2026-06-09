package com.beryllinus.backend.service;


import com.beryllinus.backend.exceptions.RoomClassNotFoundException;
import com.beryllinus.backend.model.room.RoomClass;
import com.beryllinus.backend.enumuration.RoomClassType;
import com.beryllinus.backend.repository.RoomClassRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomClassService {

    private final RoomClassRepository roomClassRepository;

    public RoomClassService(
            RoomClassRepository roomClassRepository
    ) {
        this.roomClassRepository = roomClassRepository;
    }

    /**
     * Get all active RoomClasses.
     */
    public List<RoomClass> getAllActiveRoomClasses() {

        return roomClassRepository.findAllByIsActive(true);
    }

    /**
     * Get RoomClass by id.
     */
    public RoomClass getRoomClassById(
            Integer id
    ) {

        return roomClassRepository
                .findByIdAndIsActive(id, true)
                .orElseThrow(
                        () -> new RoomClassNotFoundException(
                                "RoomClass not found. id=" + id
                        )
                );
    }

    /**
     * Get RoomClass by business enum.
     */
    public RoomClass getRoomClassByType(
            RoomClassType roomClassType
    ) {

        return roomClassRepository
                .findByRoomClassType(roomClassType)
                .orElseThrow(
                        () -> new RoomClassNotFoundException(
                                "RoomClass not found. type=" + roomClassType
                        )
                );
    }
}