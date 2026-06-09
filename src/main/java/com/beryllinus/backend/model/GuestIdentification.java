package com.beryllinus.backend.model;

import com.beryllinus.backend.enumuration.IdentificationType;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "identificationType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = GuestPassportIdentification.class, name = "passport"),
        @JsonSubTypes.Type(value = GuestDrivingLicenceIdentification.class, name = "drivingLicence"),
        @JsonSubTypes.Type(value = GuestNICIdentification.class, name = "nic")
})

public abstract class GuestIdentification {
    private IdentificationType identificationType;
    private String number;
    private String country;

}
