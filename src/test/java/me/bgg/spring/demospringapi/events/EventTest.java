package me.bgg.spring.demospringapi.events;


import me.bgg.spring.demospringapi.events.entity.Event;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EventTest {

    @Test
    void build(){
        Event event = Event.builder().build();
        assertNotNull(event);
    }

    @Test
    void javaBean(){
        String description = "Spring";
        String name = "Event";

        Event event = new Event();
        event.setName(name);
        event.setDescription(description);

        assertEquals(name, event.getName());
        assertEquals(description, event.getDescription());
    }
}