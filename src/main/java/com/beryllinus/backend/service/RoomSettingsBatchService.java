package com.beryllinus.backend.service;

import com.beryllinus.backend.model.room.RoomSetting;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

@Service
public class RoomSettingsBatchService {

    private final RoomSettingsService roomSettingsService;

    public RoomSettingsBatchService(RoomSettingsService roomSettingsService) {
        this.roomSettingsService = roomSettingsService;
    }

    //TODO: Scheduled Function
    public List<RoomSetting> createRoomSettingsAsBatch() {

        LocalDate today = LocalDate.now();
        List<RoomSetting> roomSettings = new ArrayList<>();

        // Skip Feb 29
        if (today.getMonth() == Month.FEBRUARY
                && today.getDayOfMonth() == 29) {
            return roomSettings;
        }

        LocalDate targetDate = today.plusYears(1);

        roomSettings.addAll(
                roomSettingsService.generateAndPersistRoomSettingList(targetDate)
        );

        // Feb 28 before a leap year: also generate Feb 29
        if (today.getMonth() == Month.FEBRUARY
                && today.getDayOfMonth() == 28
                && targetDate.isLeapYear()) {

            roomSettings.addAll(
                    roomSettingsService.generateAndPersistRoomSettingList(
                            targetDate.plusDays(1)
                    )
            );
        }

        return roomSettings;
    }


    private List<RoomSetting> generateAndPersistRoomSettingList(final LocalDate date) {
        return roomSettingsService.generateAndPersistRoomSettingList(date);

    }
}
