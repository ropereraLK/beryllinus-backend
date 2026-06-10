package com.beryllinus.backend.service;

import com.beryllinus.backend.dto.request.BookingPrecheckRequest;
import com.beryllinus.backend.dto.response.BookingPrecheckResponse;
import com.beryllinus.backend.enumuration.RoomClassType;
import com.beryllinus.backend.model.room.RoomClass;
import com.beryllinus.backend.model.room.RoomSetting;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    @Mock
    private RoomClassService roomClassService;

    @Mock
    private RoomSettingService roomSettingService;

    @InjectMocks
    private BookingService bookingService;

    @Test
    void shouldReturnAvailabilityForSingleRoomClass() {

        BookingPrecheckRequest request = createRequest(
                RoomClassType.EXECUTIVE,
                1,
                LocalDate.of(2026, 7, 1),
                LocalDate.of(2026, 7, 5)
        );

        RoomClass roomClass = createRoomClass(RoomClassType.EXECUTIVE);

        when(roomClassService.getRoomClassByType(RoomClassType.EXECUTIVE))
                .thenReturn(roomClass);

        when(roomSettingService.getByRoomClassAndDate(
                eq(RoomClassType.EXECUTIVE),
                any(LocalDate.class)))
                .thenReturn(
                        createRoomSetting(10, "25000", "100"),
                        createRoomSetting(10, "25000", "100"),
                        createRoomSetting(10, "25000", "100"),
                        createRoomSetting(10, "25000", "100")
                );

        BookingPrecheckResponse response =
                bookingService.getPreCheckBooking(request);

        assertNotNull(response);
        assertEquals(4, response.getNights());
        assertEquals(1, response.getRoomClasses().size());
        assertEquals(
                10,
                response.getRoomClasses().get(0).getAvailableRooms()
        );
    }

    @Test
    void shouldReturnHighestPriceAcrossStay() {

        BookingPrecheckRequest request = createRequest(
                RoomClassType.EXECUTIVE,
                1,
                LocalDate.of(2026, 7, 1),
                LocalDate.of(2026, 7, 4)
        );

        RoomClass roomClass = createRoomClass(RoomClassType.EXECUTIVE);

        when(roomClassService.getRoomClassByType(RoomClassType.EXECUTIVE))
                .thenReturn(roomClass);

        when(roomSettingService.getByRoomClassAndDate(
                eq(RoomClassType.EXECUTIVE),
                any(LocalDate.class)))
                .thenReturn(
                        createRoomSetting(10, "25000", "100"),
                        createRoomSetting(10, "35000", "200"),
                        createRoomSetting(10, "30000", "150")
                );

        BookingPrecheckResponse response =
                bookingService.getPreCheckBooking(request);

        assertEquals(
                new BigDecimal("35000"),
                response.getRoomClasses().get(0)
                        .getLocal().getPricePerNight()
        );

        assertEquals(
                new BigDecimal("200"),
                response.getRoomClasses().get(0)
                        .getInternational().getPricePerNight()
        );
    }

    @Test
    void shouldReturnMinimumAvailabilityAcrossStay() {

        BookingPrecheckRequest request = createRequest(
                RoomClassType.EXECUTIVE,
                1,
                LocalDate.of(2026, 7, 1),
                LocalDate.of(2026, 7, 4)
        );

        RoomClass roomClass = createRoomClass(RoomClassType.EXECUTIVE);

        when(roomClassService.getRoomClassByType(RoomClassType.EXECUTIVE))
                .thenReturn(roomClass);

        when(roomSettingService.getByRoomClassAndDate(
                eq(RoomClassType.EXECUTIVE),
                any(LocalDate.class)))
                .thenReturn(
                        createRoomSetting(10, "100", "100"),
                        createRoomSetting(5, "100", "100"),
                        createRoomSetting(8, "100", "100")
                );

        BookingPrecheckResponse response =
                bookingService.getPreCheckBooking(request);

        assertEquals(
                5,
                response.getRoomClasses().get(0).getAvailableRooms()
        );
    }

    @Test
    void shouldReturnEmptyResponseWhenRoomClassUnavailable() {

        BookingPrecheckRequest request = createRequest(
                RoomClassType.EXECUTIVE,
                1,
                LocalDate.of(2026, 7, 1),
                LocalDate.of(2026, 7, 4)
        );

        RoomClass roomClass = createRoomClass(RoomClassType.EXECUTIVE);

        when(roomClassService.getRoomClassByType(RoomClassType.EXECUTIVE))
                .thenReturn(roomClass);

        RoomSetting available =
                createRoomSetting(10, "100", "100");

        RoomSetting unavailable =
                createRoomSetting(0, "100", "100");

        unavailable.setCalculatedIsActive(false);

        when(roomSettingService.getByRoomClassAndDate(
                eq(RoomClassType.EXECUTIVE),
                any(LocalDate.class)))
                .thenReturn(available, unavailable, available);

        BookingPrecheckResponse response =
                bookingService.getPreCheckBooking(request);

        assertTrue(response.getRoomClasses().isEmpty());
    }

    @Test
    void shouldReturnEmptyResponseWhenRequestedRoomsExceedAvailability() {

        BookingPrecheckRequest request = createRequest(
                RoomClassType.EXECUTIVE,
                5,
                LocalDate.of(2026, 7, 1),
                LocalDate.of(2026, 7, 4)
        );

        RoomClass roomClass = createRoomClass(RoomClassType.EXECUTIVE);

        when(roomClassService.getRoomClassByType(RoomClassType.EXECUTIVE))
                .thenReturn(roomClass);

        when(roomSettingService.getByRoomClassAndDate(
                eq(RoomClassType.EXECUTIVE),
                any(LocalDate.class)))
                .thenReturn(
                        createRoomSetting(10, "100", "100"),
                        createRoomSetting(3, "100", "100"),
                        createRoomSetting(10, "100", "100")
                );

        BookingPrecheckResponse response =
                bookingService.getPreCheckBooking(request);

        assertTrue(response.getRoomClasses().isEmpty());
    }

    @Test
    void shouldReturnAllActiveRoomClassesWhenRoomTypeNotSpecified() {

        BookingPrecheckRequest request = createRequest(
                null,
                1,
                LocalDate.of(2026, 7, 1),
                LocalDate.of(2026, 7, 2)
        );

        RoomClass executive = createRoomClass(RoomClassType.EXECUTIVE);
        RoomClass luxury = createRoomClass(RoomClassType.LUXURY);

        when(roomClassService.getAllActiveRoomClasses())
                .thenReturn(List.of(executive, luxury));

        when(roomSettingService.getByRoomClassAndDate(
                any(RoomClassType.class),
                any(LocalDate.class)))
                .thenReturn(createRoomSetting(10, "100", "100"));

        BookingPrecheckResponse response =
                bookingService.getPreCheckBooking(request);

        assertEquals(2, response.getRoomClasses().size());
    }

    private BookingPrecheckRequest createRequest(
            RoomClassType roomClassType,
            Integer rooms,
            LocalDate checkIn,
            LocalDate checkOut
    ) {
        BookingPrecheckRequest request = new BookingPrecheckRequest();
        request.setRoomClassType(roomClassType);
        request.setRooms(rooms);
        request.setCheckIn(checkIn);
        request.setCheckOut(checkOut);
        return request;
    }

    private RoomClass createRoomClass(RoomClassType roomClassType) {
        RoomClass roomClass = new RoomClass();
        roomClass.setRoomClassType(roomClassType);
        return roomClass;
    }

    private RoomSetting createRoomSetting(
            int availableRooms,
            String localPrice,
            String internationalPrice
    ) {
        RoomSetting roomSetting = new RoomSetting(
                mock(RoomClass.class),
                LocalDate.now()
        );

        roomSetting.setCalculatedIsActive(true);
        roomSetting.setAvailableRooms(availableRooms);
        roomSetting.setCalcPriceLocal(new BigDecimal(localPrice));
        roomSetting.setCalcPriceInternational(
                new BigDecimal(internationalPrice)
        );

        return roomSetting;
    }
}
