package com.beryllinus.hotel_service.service;

import com.beryllinus.hotel_service.model.room.RoomSetting;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Service
public class RoomSettingsBatchService {

    private final RoomSettingsService roomSettingsService;

    public RoomSettingsBatchService(RoomSettingsService roomSettingsService) {
        this.roomSettingsService = roomSettingsService;
    }

    //TODO: Scheduled Function
    public void createRoomSettingsAsBatch() {
        LocalDate today = LocalDate.now();
        LocalDate createdDate = today.plusYears(1);
        if (today.getMonth() != Month.FEBRUARY && today.getDayOfMonth() != 29) {

            //TODO: Save to DB
            getRoomSettingList(createdDate);


            if (today.getMonth() == Month.FEBRUARY && today.getDayOfMonth() == 28 && createdDate.isLeapYear()) {
                //TODO: Save to DB
                createdDate = createdDate.plusDays(1);
                getRoomSettingList(createdDate);
            }

        }
    }

    private List<RoomSetting> getRoomSettingList(LocalDate date){
       return roomSettingsService.getRoomSettingList(date)
                .stream()
                .map(roomSettingsService::validateRoomSettings)
                .toList();
    }
}
