package com.beryllinus.hotel_service.service;

import com.beryllinus.hotel_service.dto.UserIdentification;
import com.beryllinus.hotel_service.dto.UserSearch;
import com.beryllinus.hotel_service.repository.PersonRepository;

public class PersonService {
    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public String getPersonByUserIdentification(final UserIdentification userIdentification) {
        switch (userIdentification.identificationType()) {
            case PASSPORT:
                personRepository.findByCountryAndPassportIdentificationNumber(userIdentification.issuingCountryCode(), userIdentification.identificationDocNo());
            case NIC:
                personRepository.findByNic(userIdentification.identificationDocNo());
            case DRIVING_LICENCE:
                personRepository.findByDrivingLicence(userIdentification.identificationDocNo());
            default:
                throw new RuntimeException();
        }
    }

    public String searchPerson(UserSearch userSearch) {
        return null;
    }
}
