package com.kth.mse.sep.controller;

import com.kth.mse.sep.converter.ModelConvertor;
import com.kth.mse.sep.dto.EventDto;
import com.kth.mse.sep.model.Event;
import com.kth.mse.sep.repository.EventRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
public class EventController {

    private final EventRepository eventRepository;

    private final ModelConvertor convertor;

    @GetMapping("/{eventId}")
    public EventDto getEventById(@PathVariable("eventId") Long eventId) {
        Optional<Event> event = eventRepository.findById(eventId);
        return event.map(convertor::convertEventEntityToDto).orElse(null);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public EventDto createEvent(@RequestBody EventDto event) {
        Event eventToCreate = eventRepository.save(convertor.convertEventDtoToEntity(event));
        return convertor.convertEventEntityToDto(eventToCreate);
    }

    @PatchMapping("/{eventId}")
    public EventDto patchEvent(@PathVariable("eventId") Long eventId, @RequestBody EventDto event) {
        Optional<Event> eventToUpdate = eventRepository.findById(eventId);
        return eventToUpdate.map(value -> convertor.convertEventEntityToDto(eventRepository.save(convertor.enrichEventEntity(value, event)))).orElse(null);
    }
}

