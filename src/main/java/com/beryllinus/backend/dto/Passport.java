package com.beryllinus.backend.dto;

import jakarta.persistence.Embeddable;

@Embeddable
public record Passport(
        String passportCountry,
        String identificationNumber
) {}
