package com.beryllinus.backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingPrecheckResponse {

    private LocalDate checkIn;

    private LocalDate checkOut;

    private Integer nights;

    private List<RoomAvailabilityResponse> roomClasses;
}