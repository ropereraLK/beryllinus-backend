package com.beryllinus.backend.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class GuestDrivingLicenceIdentification extends GuestIdentification {

    private LocalDate expDate;
}
