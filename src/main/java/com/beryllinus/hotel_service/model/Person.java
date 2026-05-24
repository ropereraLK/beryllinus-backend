package com.beryllinus.hotel_service.model;

import com.beryllinus.hotel_service.dto.Passport;
import lombok.Data;
import java.time.LocalDate;


@Data
public class Person {
    private Long id;
    private String firstName;
    private String lastName;

    private String country;

    private String email;
    private Passport passport;
    private Telephone telephone;
    private Telephone altTelephone;
    private String nic;
    private String drivingLicence;

    private LocalDate createdAt;
    private LocalDate updatedAt;

}
