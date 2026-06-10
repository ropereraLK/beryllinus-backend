package com.beryllinus.backend.integration;

import com.beryllinus.backend.repository.RoomClassRepository;
import com.beryllinus.backend.repository.RoomSettingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class BookingControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RoomClassRepository roomClassRepository;

    @Autowired
    private RoomSettingRepository roomSettingRepository;

    @BeforeEach
    void setup() {

        roomSettingRepository.deleteAll();
        roomClassRepository.deleteAll();

        /*
         * TODO Seed test data
         *
         * Create:
         * - RoomClassType.STANDARD
         * - active room class
         * - availableRooms > 0
         * - calculatedIsActive = true
         * - RoomSetting rows for checkIn..checkOut range
         */
    }

    @Test
    void shouldReturnBookingAvailability() throws Exception {

        LocalDate checkIn = LocalDate.now().plusDays(30);
        LocalDate checkOut = checkIn.plusDays(4);

        String payload = """
                {
                  \"checkIn\":\"%s\",
                  \"checkOut\":\"%s\",
                  \"rooms\":1
                }
                """.formatted(checkIn, checkOut);

        mockMvc.perform(
                        post("/api/v1/bookings/precheck")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(payload)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nights").value(4))
                .andExpect(jsonPath("$.roomClasses").isArray());
    }

    @Test
    void shouldReturnBadRequestWhenCheckInMissing() throws Exception {

        LocalDate checkOut = LocalDate.now().plusDays(34);

        String payload = """
                {
                  \"checkOut\":\"%s\",
                  \"rooms\":1
                }
                """.formatted(checkOut);

        mockMvc.perform(
                        post("/api/v1/bookings/precheck")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(payload)
                )
                .andExpect(status().isBadRequest());
    }
}
