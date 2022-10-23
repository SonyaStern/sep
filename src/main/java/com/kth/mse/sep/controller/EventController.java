package com.kth.mse.sep.controller;

import com.kth.mse.sep.converter.ModelConvertor;
import com.kth.mse.sep.dto.EventDto;
import com.kth.mse.sep.dto.SimpleEventDto;
import com.kth.mse.sep.model.Event;
import com.kth.mse.sep.repository.EventRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/events")
public class EventController {

    private final EventRepository eventRepository;

    private final ModelConvertor convertor;

    private List<SimpleEventDto> eventList = new ArrayList<>();

    @GetMapping("/{eventId}")
    @ResponseBody
    public EventDto getEventById(@PathVariable("eventId") Long eventId) {
        Optional<Event> event = eventRepository.findById(eventId);
        return event.map(convertor::convertEventEntityToDto).orElse(null);
    }

    @GetMapping
    public String getEvents(Model model) {
        model.addAttribute("events", eventList);
        return "events.html";
    }

    @GetMapping("/create")
    public String newEventPage() {
        return "new_event.html";
    }

    @PostMapping("/create")
    public String postEvent(Model model, SimpleEventDto event) {
        model.addAttribute("event", event);
        eventList.add(event);
        return "new_event.html";
    }

    @PostMapping
    @ResponseBody
    @ResponseStatus(value = HttpStatus.CREATED)
    public EventDto createEvent(@RequestBody EventDto event) {
        Event eventToCreate = eventRepository.save(convertor.convertEventDtoToEntity(event));
        return convertor.convertEventEntityToDto(eventToCreate);
    }

    @PatchMapping("/{eventId}")
    @ResponseBody
    public EventDto patchEvent(@PathVariable("eventId") Long eventId, @RequestBody EventDto event) {
        Optional<Event> eventToUpdate = eventRepository.findById(eventId);
        return eventToUpdate.map(value -> convertor.convertEventEntityToDto(eventRepository.save(convertor.enrichEventEntity(value, event)))).orElse(null);
    }
}

