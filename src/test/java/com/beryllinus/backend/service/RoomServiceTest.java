package com.beryllinus.backend.service;

import com.beryllinus.backend.exceptions.RoomNotFoundException;
import com.beryllinus.backend.model.room.Room;
import com.beryllinus.backend.model.room.RoomClass;
import com.beryllinus.backend.model.room.RoomConfig;
import com.beryllinus.backend.repository.RoomConfigRepository;
import com.beryllinus.backend.repository.RoomRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoomServiceTest {

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private RoomConfigRepository roomConfigRepository;

    @InjectMocks
    private RoomService roomService;

    @Test
    void shouldReturnAllActiveRooms() {
        // Business Scenario:
        // Active rooms exist in the hotel.
        // Service should return all active rooms.

        Room room = mock(Room.class);

        when(roomRepository.findAllByIsActive(true))
                .thenReturn(List.of(room));

        List<Room> result = roomService.getAllActiveRooms();

        assertEquals(1, result.size());

        verify(roomRepository).findAllByIsActive(true);
    }

    @Test
    void shouldThrowRoomNotFoundExceptionWhenNoRoomsExist() {
        // Business Scenario:
        // No active rooms exist.
        // Service should throw RoomNotFoundException.

        when(roomRepository.findAllByIsActive(true))
                .thenReturn(Collections.emptyList());

        assertThrows(RoomNotFoundException.class,
                () -> roomService.getAllActiveRooms());
    }

    @Test
    void shouldReturnRoomsBelongingToSpecifiedRoomClass() {
        // Business Scenario:
        // STANDARD -> 401,402
        // DELUXE   -> 501
        // Request STANDARD.
        // Expect only 401 and 402.

        RoomClass standard = mock(RoomClass.class);
        RoomClass deluxe = mock(RoomClass.class);

        Room room1 = mock(Room.class);
        Room room2 = mock(Room.class);
        Room room3 = mock(Room.class);

        when(room1.getRoomClass()).thenReturn(standard);
        when(room2.getRoomClass()).thenReturn(standard);
        when(room3.getRoomClass()).thenReturn(deluxe);

        when(roomRepository.findAllByIsActive(true))
                .thenReturn(List.of(room1, room2, room3));

        List<Room> result =
                roomService.getAllActiveRoomsByRoomClass(standard);

        assertEquals(2, result.size());
    }

    @Test
    void shouldReturnEmptyListWhenRoomClassHasNoRooms() {
        // Business Scenario:
        // Hotel contains STANDARD rooms only.
        // Request SUITE rooms.
        // Expect empty result.

        RoomClass standard = mock(RoomClass.class);
        RoomClass suite = mock(RoomClass.class);

        Room room1 = mock(Room.class);
        Room room2 = mock(Room.class);

        when(room1.getRoomClass()).thenReturn(standard);
        when(room2.getRoomClass()).thenReturn(standard);

        when(roomRepository.findAllByIsActive(true))
                .thenReturn(List.of(room1, room2));

        List<Room> result =
                roomService.getAllActiveRoomsByRoomClass(suite);

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldThrowExceptionWhenNoActiveRoomsExistForFiltering() {
        // Business Scenario:
        // No active rooms exist in the hotel.
        // Filtering by room class should fail.

        RoomClass roomClass = mock(RoomClass.class);

        when(roomRepository.findAllByIsActive(true))
                .thenReturn(Collections.emptyList());

        assertThrows(RoomNotFoundException.class,
                () -> roomService.getAllActiveRoomsByRoomClass(roomClass));
    }

    @Test
    void shouldReturnEmptyOptionalWhenNoRoomConfigExists() {
        // Business Scenario:
        // Room has no special configuration for the date.
        // Expect Optional.empty().

        Room room = mock(Room.class);

        when(room.getId()).thenReturn(401);

        when(roomConfigRepository.findRoomsConfigByRoomIdAndDateAndIsActive(
                eq(401), any(LocalDate.class)))
                .thenReturn(Collections.emptyList());

        Optional<RoomConfig> result =
                roomService.getRoomConfigByDate(room, LocalDate.now());

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldReturnRoomConfigWhenSingleConfigExists() {
        // Business Scenario:
        // One matching RoomConfig exists.
        // Expect that config to be returned.

        Room room = mock(Room.class);
        RoomConfig config = mock(RoomConfig.class);

        when(room.getId()).thenReturn(401);

        when(roomConfigRepository.findRoomsConfigByRoomIdAndDateAndIsActive(
                eq(401), any(LocalDate.class)))
                .thenReturn(List.of(config));

        Optional<RoomConfig> result =
                roomService.getRoomConfigByDate(room, LocalDate.now());

        assertTrue(result.isPresent());
        assertEquals(config, result.get());
    }

    @Test
    void shouldReturnLatestRoomConfigWhenMultipleConfigsExist() {
        // Business Scenario:
        // Multiple configs match the same date.
        // Latest updated config should win.

        Room room = mock(Room.class);

        RoomConfig oldConfig = mock(RoomConfig.class);
        RoomConfig newConfig = mock(RoomConfig.class);

        when(room.getId()).thenReturn(401);

        when(oldConfig.getUpdatedAt())
                .thenReturn(LocalDateTime.of(2026, 1, 1, 0, 0));

        when(newConfig.getUpdatedAt())
                .thenReturn(LocalDateTime.of(2026, 2, 1, 0, 0));

        when(roomConfigRepository.findRoomsConfigByRoomIdAndDateAndIsActive(
                eq(401), any(LocalDate.class)))
                .thenReturn(List.of(oldConfig, newConfig));

        Optional<RoomConfig> result =
                roomService.getRoomConfigByDate(room, LocalDate.now());

        assertTrue(result.isPresent());
        assertEquals(newConfig, result.get());
    }
}
