package com.beryllinus.hotel_service.model;

import jakarta.persistence.Embeddable;
import lombok.Data;
@Embeddable
@Data
public class Telephone {

    private String telephoneNumber;
    private String countryCode;
    private boolean isWhatsappAvailable;
}
