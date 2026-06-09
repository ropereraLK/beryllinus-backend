package com.beryllinus.backend.model;

import jakarta.persistence.Embeddable;

@Embeddable
public record Telephone(
        String telephoneNumber,
        String countryCode,
        boolean isWhatsappAvailable
) {
}
