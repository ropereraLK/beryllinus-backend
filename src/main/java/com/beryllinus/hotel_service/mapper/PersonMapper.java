package com.beryllinus.hotel_service.mapper;

import com.beryllinus.hotel_service.dto.response.PersonResponse;
import com.beryllinus.hotel_service.model.Person;
import org.springframework.stereotype.Component;

@Component
public class PersonMapper {
    public PersonResponse toPersonResponse(Person person) {

        PersonResponse response = new PersonResponse();

        response.setFirstName(person.getFirstName());
        response.setLastName(person.getLastName());

        response.setCountry(person.getCountry());
        response.setEmail(person.getEmail());

        if (person.getPassport() != null) {

            response.setPassportCountry(
                    person.getPassport().country()
            );

            response.setPassportIdentificationNumber(
                    person.getPassport().identificationNumber()
            );
        }

        if (person.getTelephone() != null) {

            response.setTelephoneTelephoneNumber(
                    person.getTelephone().telephoneNumber()
            );

            response.setTelephoneCountryCode(
                    person.getTelephone().countryCode()
            );

            response.setTelephoneIsWhatsappAvailable(
                    person.getTelephone().isWhatsappAvailable()
            );
        }

        if (person.getAltTelephone() != null) {

            response.setAltTelephoneTelephoneNumber(
                    person.getAltTelephone().telephoneNumber()
            );

            response.setAltTelephoneCountryCode(
                    person.getAltTelephone().countryCode()
            );

            response.setAltTelephoneIsWhatsappAvailable(
                    person.getAltTelephone().isWhatsappAvailable()
            );
        }

        response.setNic(person.getNic());
        response.setDrivingLicence(person.getDrivingLicence());

        return response;
    }
}
