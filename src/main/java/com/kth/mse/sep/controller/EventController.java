package com.kth.mse.sep.controller;

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

    @GetMapping("/{eventId}")
    public Event getEventById(@PathVariable("eventId") Long eventId) {
        return eventRepository.findById(eventId)
                .orElse(null);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public Event createEvent(@RequestBody Event event) {
        return eventRepository.save(event);
    }

    @PatchMapping("/{eventId}")
    public Event patchEvent(@PathVariable("eventId") Long eventId, @RequestBody Event event) {
        Optional<Event> eventToUpdate = eventRepository.findById(eventId);
        if (eventToUpdate.isPresent()) {
            Event updatedEvent = eventToUpdate.get()
                    .setEventDate(event.getEventDate())
                    .setAddress(event.getAddress())
                    .setClient(event.getClient())
                    .setFinancialFeedback(event.getFinancialFeedback());
            updatedEvent.setCreateDate(event.getCreateDate())
                    .setLastUpdateDate(event.getLastUpdateDate())
                    .setStatus(event.getStatus());
            return eventRepository.save(updatedEvent);
        }
        return null;
    }
}

