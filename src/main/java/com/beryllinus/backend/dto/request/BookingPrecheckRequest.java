package com.beryllinus.backend.dto.request;

import com.beryllinus.backend.enumuration.RoomClassType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BookingPrecheckRequest {

    @NotNull
    private LocalDate checkIn;

    private LocalDate checkOut;

    private Integer rooms;

    private RoomClassType roomClassType;
}
