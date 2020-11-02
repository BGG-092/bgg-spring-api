package me.bgg.spring.demospringapi.events.controller;


import me.bgg.spring.demospringapi.events.validators.EventValidator;
import me.bgg.spring.demospringapi.events.dto.EventDto;
import me.bgg.spring.demospringapi.events.entity.Event;
import me.bgg.spring.demospringapi.events.repository.EventRepository;
import me.bgg.spring.demospringapi.events.service.EventService;
import org.modelmapper.ModelMapper;

import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Controller
@RequestMapping(value = "/api/events", produces = MediaTypes.HAL_JSON_VALUE)
public class EventController {

    private  final EventRepository eventRepository;

    private  final ModelMapper modelMapper;

    private final EventValidator eventValidator;

    private final EventService eventService;

    public EventController(EventRepository eventRepository, ModelMapper modelMapper, EventValidator eventValidator, EventService eventService) {
        this.eventRepository = eventRepository;
        this.modelMapper = modelMapper;
        this.eventValidator = eventValidator;
        this.eventService = eventService;
    }


    @PostMapping
    public ResponseEntity createEvent(@RequestBody @Valid EventDto eventDto, Errors errors) throws Exception {

        if(errors.hasErrors()){
            return  ResponseEntity.badRequest().body(errors);
        }

        eventValidator.validate(eventDto, errors);
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors);
        }

        Event event = modelMapper.map(eventDto, Event.class);

        Event newEvent = eventService.update(event);
        URI createdUri = linkTo(EventController.class).slash(newEvent.getId()).toUri();
        return ResponseEntity.created(createdUri).body(event);
    }

}
