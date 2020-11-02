package me.bgg.spring.demospringapi.events.service.impl;

import me.bgg.spring.demospringapi.events.entity.Event;
import me.bgg.spring.demospringapi.events.repository.EventRepository;
import me.bgg.spring.demospringapi.events.service.EventService;
import org.springframework.stereotype.Service;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public Event update(Event event) throws Exception {

        if (event.getBasePrice() == 0 && event.getMaxPrice() == 0) {
            event.setFree(true);
        } else {
            event.setFree(false);
        }

        if (event.getLocation() == null || event.getLocation().isBlank()) {
            event.setOffline(false);
        } else {
            event.setOffline(true);
        }

        return this.eventRepository.save(event);
    }
}
