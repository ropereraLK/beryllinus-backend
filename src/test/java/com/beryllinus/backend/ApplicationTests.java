package com.beryllinus.backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest(
        properties = {
                "spring.autoconfigure.exclude=org.springframework.boot.kafka.autoconfigure.KafkaAutoConfiguration"
        }
)
@ActiveProfiles("test")
class ApplicationTests {

	@MockitoBean
	private KafkaTemplate<String, String> kafkaTemplate;
    @Test
    void contextLoads() {
    }

}
