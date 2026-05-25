package com.beryllinus.hotel_service.dto;

import jakarta.persistence.Embeddable;

@Embeddable
public record Passport(
        String passportCountry,
        String identificationNumber
) {}
