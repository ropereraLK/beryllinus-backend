package com.beryllinus.backend.integration;


import com.beryllinus.backend.dto.response.PersonDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class PersonIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreatePerson() throws Exception {

        PersonDTO dto = new PersonDTO();

        dto.setFirstName("Rohan");
        dto.setLastName("Perera");
        dto.setCountry("LK");
        dto.setEmail("rohan@test.com");

        dto.setPassportCountry("LK");
        dto.setPassportIdentificationNumber("N1234567");

        dto.setTelephoneTelephoneNumber("771234567");
        dto.setTelephoneCountryCode("+94");
        dto.setTelephoneIsWhatsappAvailable(true);

        dto.setAltTelephoneTelephoneNumber("712345678");
        dto.setAltTelephoneCountryCode("+94");
        dto.setAltTelephoneIsWhatsappAvailable(false);

        dto.setNic("200012345678");
        dto.setDrivingLicence("B123456");

        mockMvc.perform(
                        post("/api/people")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto))
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("Rohan"))
                .andExpect(jsonPath("$.lastName").value("Perera"))
                .andExpect(jsonPath("$.email").value("rohan@test.com"))
                .andExpect(jsonPath("$.country").value("LK"));
    }
}