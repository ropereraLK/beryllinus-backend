package com.beryllinus.backend.dto.response;

import com.beryllinus.backend.enumuration.RoomClassType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomAvailabilityResponse {

    private RoomClassType roomClassType;

    private Integer availableRooms;

    private PriceResponse local;

    private PriceResponse international;
}