package com.beryllinus.backend.dto;

import com.beryllinus.backend.enumuration.Currency;
import com.beryllinus.backend.enumuration.RoomClassType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
@Data
@Builder
public class RoomResponse {

    private RoomClassType roomClassType;
    private boolean calculatedIsActive;

    private BigDecimal calcPriceLocal;
    @Enumerated(EnumType.STRING)
    private Currency calcPriceLocalCurrency;
    private boolean calIsLocalBookingActive;

    private BigDecimal calcPriceInternational;
    @Enumerated(EnumType.STRING)
    private Currency calcPriceInternationalCurrency;
    private boolean calcIsInternationalBookingActive;
}
