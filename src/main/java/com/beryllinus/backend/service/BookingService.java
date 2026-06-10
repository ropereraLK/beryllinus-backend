package com.beryllinus.backend.service;

import com.beryllinus.backend.dto.request.BookingPrecheckRequest;
import com.beryllinus.backend.dto.response.BookingPrecheckResponse;
import com.beryllinus.backend.dto.response.PriceResponse;
import com.beryllinus.backend.dto.response.RoomAvailabilityResponse;
import com.beryllinus.backend.model.room.RoomClass;
import com.beryllinus.backend.model.room.RoomSetting;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookingService {
    private final RoomClassService roomClassService;
    private final RoomSettingService roomSettingService;

    public BookingService(
            RoomClassService roomClassService,
            RoomSettingService roomSettingService) {
        this.roomClassService = roomClassService;
        this.roomSettingService = roomSettingService;
    }

    public BookingPrecheckResponse getPreCheckBooking(
            BookingPrecheckRequest bookingPrecheckRequest
    ) {

        List<RoomClass> roomClassList = new ArrayList<>();

        if (bookingPrecheckRequest.getRoomClassType() == null) {
            roomClassList.addAll(roomClassService.getAllActiveRoomClasses());
        } else {
            roomClassList.add(
                    roomClassService.getRoomClassByType(
                            bookingPrecheckRequest.getRoomClassType()
                    )
            );
        }

        List<RoomAvailabilityResponse> roomClasses = new ArrayList<>();

        for (RoomClass roomClass : roomClassList) {

            int minAvailableRooms = Integer.MAX_VALUE;

            BigDecimal highestLocalPrice = BigDecimal.ZERO;
            BigDecimal highestInternationalPrice = BigDecimal.ZERO;

            boolean available = true;

            LocalDate date = bookingPrecheckRequest.getCheckIn();

            while (date.isBefore(bookingPrecheckRequest.getCheckOut())) {

                RoomSetting roomSetting =
                        roomSettingService.getByRoomClassAndDate(
                                roomClass.getRoomClassType(),
                                date
                        );

                if (!roomSetting.isCalculatedIsActive()
                        || roomSetting.getAvailableRooms() < bookingPrecheckRequest.getRooms()) {

                    available = false;
                    break;
                }

                minAvailableRooms = Math.min(
                        minAvailableRooms,
                        roomSetting.getAvailableRooms()
                );

                highestLocalPrice = highestLocalPrice.max(
                        roomSetting.getCalcPriceLocal()
                );

                highestInternationalPrice = highestInternationalPrice.max(
                        roomSetting.getCalcPriceInternational()
                );

                date = date.plusDays(1);
            }

            if (!available) {
                continue;
            }

            RoomAvailabilityResponse.RoomAvailabilityResponseBuilder builder =
                    RoomAvailabilityResponse.builder()
                            .roomClassType(roomClass.getRoomClassType())
                            .availableRooms(minAvailableRooms);

            if (highestLocalPrice.compareTo(BigDecimal.ZERO) > 0) {
                builder.local(
                        PriceResponse.builder()
                                .pricePerNight(highestLocalPrice)
                                .currency("LKR")
                                .build()
                );
            }

            if (highestInternationalPrice.compareTo(BigDecimal.ZERO) > 0) {
                builder.international(
                        PriceResponse.builder()
                                .pricePerNight(highestInternationalPrice)
                                .currency("USD")
                                .build()
                );
            }

            roomClasses.add(builder.build());
        }

        return BookingPrecheckResponse.builder()
                .checkIn(bookingPrecheckRequest.getCheckIn())
                .checkOut(bookingPrecheckRequest.getCheckOut())
                .nights(
                        (int) ChronoUnit.DAYS.between(
                                bookingPrecheckRequest.getCheckIn(),
                                bookingPrecheckRequest.getCheckOut()
                        )
                )
                .roomClasses(roomClasses)
                .build();
    }
}
