package com.beryllinus.backend.model.room;

import com.beryllinus.backend.enumuration.Currency;
import com.beryllinus.backend.enumuration.ReservationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RoomBooking {

    private int roomId;
    private Date date;
    private UUID bookingId;
    private ReservationType reservationType;
    private double basePrice;
    private Currency basePriceCurrency;



}
