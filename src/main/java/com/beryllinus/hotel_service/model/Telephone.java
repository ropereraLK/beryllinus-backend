package com.beryllinus.hotel_service.model;

import jakarta.persistence.Embeddable;

@Embeddable
public record Telephone(
        String telephoneNumber,
        String countryCode,
        boolean isWhatsappAvailable
) {
}
