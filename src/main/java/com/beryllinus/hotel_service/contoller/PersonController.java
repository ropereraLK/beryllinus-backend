package com.beryllinus.hotel_service.contoller;

import com.beryllinus.hotel_service.dto.UserIdentification;
import com.beryllinus.hotel_service.dto.UserSearch;
import com.beryllinus.hotel_service.repository.RoomConfigRepository;
import com.beryllinus.hotel_service.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/people")
public class PersonController {

    private final PersonService personService;
    @Autowired
    private RoomConfigRepository roomConfigRepository;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public ResponseEntity<String> getPerson(@RequestBody UserIdentification userIdentification) {
        return ResponseEntity.ok(personService.getPersonByUserIdentification(userIdentification));

    }

    @GetMapping
    public ResponseEntity<String> searchPerson(@RequestBody UserSearch userSearch) {
        return ResponseEntity.ok(personService.searchPerson(userSearch));

    }
}
