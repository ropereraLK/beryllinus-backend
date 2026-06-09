package com.beryllinus.backend.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoomSettingsBatchServiceTest {
    @Mock
    private RoomSettingsService roomSettingsService;

    private RoomSettingsBatchService service;

    //    01: Test a normal day
    //    Today: 2026-06-09 -> Generated For 2027-06-09
    @Test
    void shouldGenerateSettingsForNextYearOnNormalDay() {

        Clock fixedClock = Clock.fixed(
                Instant.parse("2026-06-09T00:00:00Z"),
                ZoneOffset.UTC
        );

        service = new RoomSettingsBatchService(
                roomSettingsService,
                fixedClock
        );

        when(roomSettingsService.generateAndPersistRoomSettingList(any()))
                .thenReturn(Collections.emptyList());

        service.createRoomSettingsAsBatch();

        verify(roomSettingsService)
                .generateAndPersistRoomSettingList(
                        LocalDate.of(2027, 6, 9)
                );
    }

    //   02: Test a 29th February
    //   Skip setting generation
    @Test
    void shouldSkipOnFeb29() {

        Clock fixedClock = Clock.fixed(
                Instant.parse("2028-02-29T00:00:00Z"),
                ZoneOffset.UTC
        );

        service = new RoomSettingsBatchService(
                roomSettingsService,
                fixedClock
        );

        service.createRoomSettingsAsBatch();

        verifyNoInteractions(roomSettingsService);
    }

    //   03: Test a 28th February & next year a leap year
    //   Generate for 28th and 29th February
    @Test
    void shouldGenerateFeb28AndFeb29ForNextLeapYear() {

        Clock fixedClock = Clock.fixed(
                Instant.parse("2027-02-28T00:00:00Z"),
                ZoneOffset.UTC
        );

        service = new RoomSettingsBatchService(
                roomSettingsService,
                fixedClock
        );

        when(roomSettingsService.generateAndPersistRoomSettingList(any()))
                .thenReturn(Collections.emptyList());

        service.createRoomSettingsAsBatch();

        verify(roomSettingsService)
                .generateAndPersistRoomSettingList(
                        LocalDate.of(2028, 2, 28)
                );

        verify(roomSettingsService)
                .generateAndPersistRoomSettingList(
                        LocalDate.of(2028, 2, 29)
                );

        verify(roomSettingsService, times(2))
                .generateAndPersistRoomSettingList(any());
    }

    //   03: Test a 28th February & next year non leap year
    //   Generate for 28th only
    @Test
    void shouldGenerateOnlyFeb28WhenNextYearIsNotLeapYear() {

        Clock fixedClock = Clock.fixed(
                Instant.parse("2026-02-28T00:00:00Z"),
                ZoneOffset.UTC
        );

        service = new RoomSettingsBatchService(
                roomSettingsService,
                fixedClock
        );

        when(roomSettingsService.generateAndPersistRoomSettingList(any()))
                .thenReturn(Collections.emptyList());

        service.createRoomSettingsAsBatch();

        verify(roomSettingsService)
                .generateAndPersistRoomSettingList(
                        LocalDate.of(2027, 2, 28)
                );

        verify(roomSettingsService, times(1))
                .generateAndPersistRoomSettingList(any());
    }
}