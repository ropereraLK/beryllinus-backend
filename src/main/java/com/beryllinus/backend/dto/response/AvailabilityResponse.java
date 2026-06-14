package com.beryllinus.backend.dto.response;

import com.beryllinus.backend.dto.Price;
import com.beryllinus.backend.dto.RoomResponse;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class AvailabilityResponse {
    private LocalDate date;
    //TODO
//    private boolean isBookingActive;
//    private int totalRoomsAvailable;
//    private int totalRoomsBooked;
//    private Price lowestRoomPriceLocal;
//    private Price lowestRoomPriceInternational;
    private List<RoomResponse> rooms;
    private Instant generatedAt;

}