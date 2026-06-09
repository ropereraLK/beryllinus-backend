package com.beryllinus.backend.service;

import com.beryllinus.backend.exceptions.RoomClassConfigNotFoundException;
import com.beryllinus.backend.exceptions.RoomClassNotFoundException;
import com.beryllinus.backend.model.room.RoomClass;
import com.beryllinus.backend.model.room.RoomClassConfig;
import com.beryllinus.backend.repository.RoomClassConfigRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomClassConfigService {

    private final RoomClassConfigRepository roomClassConfigRepository;
    private final RoomClassService roomClassService;

    public RoomClassConfigService(
            RoomClassConfigRepository roomClassConfigRepository,
            RoomClassService roomClassService
    ) {
        this.roomClassConfigRepository = roomClassConfigRepository;
        this.roomClassService = roomClassService;
    }

    /**
     * Get all RoomClassConfigs.
     */
    public List<RoomClassConfig> getAll() {

        return roomClassConfigRepository.findAll();
    }

    /**
     * Get RoomClassConfig by id.
     */
    public RoomClassConfig getById(
            Integer id
    ) {

        return roomClassConfigRepository
                .findById(id)
                .orElseThrow(
                        () -> new RoomClassConfigNotFoundException(
                                "RoomClassConfig not found. id=" + id
                        )
                );
    }

    /**
     * Get all configs belonging to a RoomClass.
     */
    public List<RoomClassConfig> getByRoomClass(
            Integer roomClassId
    ) {

        RoomClass roomClass =
                roomClassService.getRoomClassById(roomClassId);

        return roomClassConfigRepository
                .findByRoomClass(roomClass);
    }

    /**
     * Create RoomClassConfig.
     */
    public RoomClassConfig create(
            RoomClassConfig roomClassConfig
    ) {

        validateRoomClassExists(
                roomClassConfig.getRoomClass()
        );

        return roomClassConfigRepository.save(
                roomClassConfig
        );
    }

    /**
     * Update RoomClassConfig.
     */
    public RoomClassConfig update(
            Integer id,
            RoomClassConfig request
    ) {

        RoomClassConfig existing =
                getById(id);

        validateRoomClassExists(
                request.getRoomClass()
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

        existing.setPriceLocal(
                request.getPriceLocal()
        );

        existing.setPriceLocalCurrency(
                request.getPriceLocalCurrency()
        );

        existing.setLocalBookingActive(
                request.isLocalBookingActive()
        );

        existing.setPriceInternational(
                request.getPriceInternational()
        );

        existing.setPriceInternationalCurrency(
                request.getPriceInternationalCurrency()
        );

        existing.setInternationalBookingActive(
                request.isInternationalBookingActive()
        );

        return roomClassConfigRepository.save(
                existing
        );
    }

    /**
     * Verify RoomClass exists before persisting config.
     */
    private void validateRoomClassExists(
            RoomClass roomClass
    ) {

        if (roomClass == null) {
            throw new RoomClassNotFoundException(
                    "RoomClass cannot be null"
            );
        }

        roomClassService.getRoomClassById(
                roomClass.getId()
        );
    }
}