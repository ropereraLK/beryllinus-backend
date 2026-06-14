package com.beryllinus.backend.dto;

import com.beryllinus.backend.enumuration.Currency;

public record Price(
        Currency currency,
        double amount
) {
}
