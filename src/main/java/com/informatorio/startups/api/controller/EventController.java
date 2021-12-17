package com.informatorio.startups.api.controller;

import java.util.List;
import java.util.Optional;

import com.informatorio.startups.api.entity.Event;
import com.informatorio.startups.api.repository.EventRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/events")
public class EventController {

    @Autowired
    private EventRepository eventRepository;

    // READ events
    @GetMapping
    public ResponseEntity<List<Event>> getEvents() {
        try {
            List<Event> events;
            events = eventRepository.findAll();
            if (events.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(events, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // CREATE event
    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {
        try {
            return new ResponseEntity<>(eventRepository.save(event), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // UPDATE event
    @PutMapping(value = "/{id}")
    public ResponseEntity<Event> updateUser(@PathVariable("id") Long id, @RequestBody Event event) {
        Optional<Event> eventData = eventRepository.findById(id);
        if (eventData.isPresent()) {
            Event _event = eventData.get();
            _event.setStatus(event.getStatus());
            _event.setDescription(event.getDescription());
            _event.setStartDate(event.getStartDate());
            _event.setEndDate(event.getEndDate());
            _event.setAward(event.getAward());
            
            return new ResponseEntity<>(eventRepository.save(_event), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // DELETE event by id
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<HttpStatus> deleteGenre(@PathVariable("id") Long id) {
        try {
            eventRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
