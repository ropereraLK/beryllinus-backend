package com.beryllinus.backend.service;

import com.beryllinus.backend.exceptions.RoomConfigNotFoundException;
import com.beryllinus.backend.exceptions.RoomNotFoundException;
import com.beryllinus.backend.model.room.Room;
import com.beryllinus.backend.model.room.RoomClass;
import com.beryllinus.backend.model.room.RoomConfig;
import com.beryllinus.backend.repository.RoomConfigRepository;
import com.beryllinus.backend.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final RoomConfigRepository roomConfigRepository;

    public RoomService(RoomRepository roomRepository, RoomConfigRepository roomConfigRepository) {
        this.roomRepository = roomRepository;
        this.roomConfigRepository = roomConfigRepository;
    }

    public List<Room> getAllActiveRooms() throws RoomNotFoundException {
        List<Room> roomList = roomRepository.findAllByIsActive(true);

        if (roomList.isEmpty()) {
            throw new RoomNotFoundException();
        } else {
            return roomList;
        }
    }

    public List<Room> getAllActiveRoomsByRoomClass(RoomClass roomClass) throws RoomNotFoundException {
        return getAllActiveRooms().stream()
                .filter(r -> r.getRoomClass().equals(roomClass))
                .toList();
    }

    public Optional<RoomConfig> getRoomConfigByDate(Room room, LocalDate date) throws RoomConfigNotFoundException {
        List<RoomConfig> roomConfigList = roomConfigRepository.findRoomsConfigByRoomIdAndDateAndIsActive(room.getId(), date);
        if (roomConfigList.isEmpty()) {
            return Optional.empty();
        } else if (roomConfigList.size() > 1) {
            //TODO: Trigger Correction needed error
            return roomConfigList.stream().max(Comparator.comparing(RoomConfig::getUpdatedAt));
        } else {
            return Optional.of(roomConfigList.getFirst());
        }
    }

    public List<Room> getRoomsByRoomClass(Long roomClassId) {

        return roomRepository.findByRoomClass_IdAndIsActive(
                roomClassId,
                true
        );
    }

    public Room getRoomById(Integer id) {

        return roomRepository
                .findByIdAndIsActive(id, true)
                .orElseThrow(() ->
                        new RoomNotFoundException(
                                "Room not found. id=" + id
                        )
                );
    }

}
