package me.bgg.spring.demospringapi.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.bgg.spring.demospringapi.events.dto.EventDto;
import me.bgg.spring.demospringapi.events.entity.Event;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class EventControllerTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void createEvent() throws Exception {
        EventDto eventDto = EventDto.builder()
                .name("Spring")
                .description("REST API")
                .beginEnrollmentDateTime(LocalDateTime.of(2020,8,31,22,10))
                .closeEnrollmentDateTime(LocalDateTime.of(2020,9,30,22,10))
                .beginEventDateTime(LocalDateTime.of(2020,8,31,22,10))
                .endEventDateTime(LocalDateTime.of(2020,9,30,22,10))
                .maxPrice(10000)
                .basePrice(1000)
                .limitOfEnrollment(100)
                .location("경기도 성남시 어딘가")
                .build();


        mockMvc.perform(post("/api/events/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaTypes.HAL_JSON)
                .content(objectMapper.writeValueAsString(eventDto)))
                .andDo(print())
                .andExpect(status().isCreated())
//                .andExpect(jsonPath("id").exists())
//                .andExpect(header().exists(HttpHeaders.LOCATION))
//                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
//                .andExpect(jsonPath("free").value(Matchers.not(true)))
//                .andExpect(jsonPath("eventStatus").value(EventStatus.DRAFT.name()))
//                .andExpect(status().isBadRequest())
        ;

    }

    @Test
    @DisplayName("입력 값이 비어있는 경우 에러가 발생하는 테스트")
    void createEvent_Bad_Request_Empty_Input() throws Exception {
        EventDto eventDto = EventDto.builder()
                .description("description TEST")
                .build();

        this.mockMvc.perform(post("/api/events")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(this.objectMapper.writeValueAsString(eventDto)))
                    .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void createEvent_Bad_Request_Wrong_Input() throws Exception {
        EventDto eventDto = EventDto.builder()
                .name("Spring")
                .description("REST API")
                .beginEnrollmentDateTime(LocalDateTime.of(2020,8,31,22,10))
                .closeEnrollmentDateTime(LocalDateTime.of(2020,8,30,22,10))
                .beginEventDateTime(LocalDateTime.of(2020,8,31,22,10))
                .endEventDateTime(LocalDateTime.of(2020,8,30,22,10))
                .maxPrice(100)
                .basePrice(1000)
                .limitOfEnrollment(100)
                .build();

        this.mockMvc.perform(post("/api/events")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(this.objectMapper.writeValueAsString(eventDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field").exists())
                .andExpect(jsonPath("$[0].code").exists())
                .andExpect(jsonPath("$[0].defaultMessage").exists())
                ;
    }

    @Test
    void testFree() {
        Event event = Event.builder()
                .basePrice(0)
                .maxPrice(0)
                .build();
    }


}
