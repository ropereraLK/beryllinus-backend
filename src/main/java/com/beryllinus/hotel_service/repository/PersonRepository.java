package com.beryllinus.hotel_service.repository;

import com.beryllinus.hotel_service.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> findByNic(String nic);

    Optional<Person> findByDrivingLicence(String drivingLicence);

    Optional<Person>
    findByCountryAndPassportIdentificationNumber(
            String country,
            String identificationNumber
    );
}
