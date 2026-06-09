package com.beryllinus.backend.service;

import com.beryllinus.backend.exceptions.RoomClassConfigNotFoundException;
import com.beryllinus.backend.exceptions.RoomNotFoundException;
import com.beryllinus.backend.model.room.*;
import com.beryllinus.backend.repository.RoomClassConfigRepository;
import com.beryllinus.backend.repository.RoomClassRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class RoomSettingsService {

    private final RoomClassConfigRepository roomClassConfigRepository;
    private final RoomClassRepository roomClassRepository;
    private final RoomService roomService;

    public RoomSettingsService(
            RoomClassConfigRepository roomClassConfigRepository,
            RoomService roomService,
            RoomClassRepository roomClassRepository
    ) {

        this.roomClassConfigRepository = roomClassConfigRepository;
        this.roomService = roomService;
        this.roomClassRepository = roomClassRepository;
    }

    /**
     * @param date: date
     */
    public List<RoomSetting> getRoomSettingList(final LocalDate date) throws RoomNotFoundException {
        //Get All Room Classes
        List<RoomClass> roomClassList = roomClassRepository.findAllByIsActive(true);

        List<RoomSetting> roomSettingList = new ArrayList<>();
        roomClassList.forEach(
                roomClass ->
                        roomSettingList.add(getRoomSettings(roomClass, date))
        );
        return roomSettingList;
    }

    /**
     * @param roomClass: roomClass
     *                   By the repository layer only active roomClasses are fetched
     */
    public RoomSetting getRoomSettings(RoomClass roomClass, LocalDate date) throws RoomNotFoundException {
        //Create the new RoomSetting
        RoomSetting roomSetting = new RoomSetting(roomClass);

        //Validate RoomSetting with RoomClass
        validateRoomSettingWithRoomClass(roomSetting, roomClass);

        try {
            validateRoomSettingWithRoomClassConfig(roomSetting, getRoomClasConfig(roomClass, date));

        } catch (RoomClassConfigNotFoundException e) {
            //No Room Config Found, Use Room Base Data
            //TODO: Log this
        }

        //Calculate the number of available rooms for that date
        roomSetting.setAvailableRooms(calculateRoomAvailability(roomClass, date));

        return roomSetting;
    }


    private void validateRoomSettingWithRoomClass(RoomSetting roomSetting, RoomClass roomClass) {
        roomSetting.setRoomClass(roomClass);
        roomSetting.setBaseIsActive(roomClass.isActive());

        roomSetting.setBaseIsLocalBookingActive(roomClass.isLocalBookingActive());
        roomSetting.setBasePriceLocal(roomClass.getPriceLocal());
        roomSetting.setBasePriceLocalCurrency(roomClass.getPriceLocalCurrency());


        roomSetting.setBaseIsInternationalBookingActive(roomClass.isInternationalBookingActive());
        roomSetting.setBasePriceInternationalCurrency(roomClass.getPriceInternationalCurrency());
        roomSetting.setBasePriceInternational(roomClass.getPriceInternational());


    }

    private Optional<RoomClassConfig> getRoomClasConfig(RoomClass roomClass, LocalDate date) {
        Optional<List<RoomClassConfig>> roomClassConfigList = roomClassConfigRepository.findRoomsClassConfigByIdAndDateAndIsActive(roomClass.getId(), date);
        if (roomClassConfigList.isEmpty()) {
            return Optional.empty();
        } else if (roomClassConfigList.get().size() == 1) {
            return Optional.of(roomClassConfigList.get().getFirst());
        } else {
            //TODO: Corrections needed trigger
            return Optional.of(roomClassConfigList.get().stream()
                    .max(Comparator.comparing(RoomClassConfig::getUpdatedAt))
                    .orElseThrow(RoomClassConfigNotFoundException::new));
        }
    }

    /**
     * @param roomClassConfig: 401
     * @param roomSetting:     2015-01-28
     */
    private void validateRoomSettingWithRoomClassConfig(RoomSetting roomSetting, Optional<RoomClassConfig> roomClassConfig) throws RoomClassConfigNotFoundException {

        if (roomClassConfig.isEmpty()) {

            //If RoomClassConfig Not Found update the Base values (value from RoomClass as default values)
            roomSetting.setCalculatedIsActive(roomSetting.isBaseIsActive());

            roomSetting.setCalcPriceLocal(roomSetting.getBasePriceLocal());
            roomSetting.setCalcPriceLocalCurrency(roomSetting.getBasePriceLocalCurrency());
            roomSetting.setCalIsLocalBookingActive(roomSetting.isBaseIsLocalBookingActive());

            roomSetting.setCalcPriceInternational(roomSetting.getBasePriceInternational());
            roomSetting.setCalcPriceInternationalCurrency(roomSetting.getBasePriceInternationalCurrency());
            roomSetting.setCalcIsInternationalBookingActive(roomSetting.isBaseIsInternationalBookingActive());

        } else {
            roomSetting.setCalculatedIsActive(roomClassConfig.get().isActive());

            roomSetting.setCalcPriceLocal(roomClassConfig.get().getPriceLocal());
            roomSetting.setCalcPriceLocalCurrency(roomClassConfig.get().getPriceLocalCurrency());
            roomSetting.setCalIsLocalBookingActive(roomClassConfig.get().isLocalBookingActive());

            roomSetting.setCalcPriceInternational(roomClassConfig.get().getPriceInternational());
            roomSetting.setCalcPriceInternationalCurrency(roomClassConfig.get().getPriceInternationalCurrency());
            roomSetting.setCalcIsInternationalBookingActive(roomClassConfig.get().isInternationalBookingActive());
        }


    }

    private int calculateRoomAvailability(RoomClass roomClass, LocalDate date) {

        int availableRooms = 0;

        for (Room room : roomService.getAllActiveRoomsByRoomClass(roomClass)) {
            Optional<RoomConfig> configOpt =
                    roomService.getRoomConfigByDate(room, date);

            if (configOpt.isEmpty() || configOpt.get().isActive()) {
                availableRooms++;
            }
        }

        return availableRooms;
    }


}
