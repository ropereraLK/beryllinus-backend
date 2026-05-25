package com.beryllinus.hotel_service.model;

import com.beryllinus.hotel_service.dto.Passport;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "person")
@Data
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;

    private String country;

    private String email;
    @Embedded
    private Passport passport;
    @Embedded
    private Telephone telephone;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(
                    name = "telephoneNumber",
                    column = @Column(name = "alt_telephone_number")
            ),
            @AttributeOverride(
                    name = "countryCode",
                    column = @Column(name = "alt_country_code")
            ),
            @AttributeOverride(
                    name = "isWhatsappAvailable",
                    column = @Column(name = "alt_whatsapp_available")
            )
    })
    private Telephone altTelephone;
    private String nic;
    private String drivingLicence;

    private LocalDate createdAt;
    private LocalDate updatedAt;

}
