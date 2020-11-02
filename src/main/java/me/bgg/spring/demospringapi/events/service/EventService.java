package me.bgg.spring.demospringapi.events.service;

import me.bgg.spring.demospringapi.events.entity.Event;

public interface EventService {

    Event update(Event event) throws Exception;
}
