package com.informatorio.startups.api.controller;

import java.util.List;
import java.util.Optional;

import com.informatorio.startups.api.entity.Startup;
import com.informatorio.startups.api.entity.User;
import com.informatorio.startups.api.repository.StartupRepository;
import com.informatorio.startups.api.repository.UserRepository;

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
@RequestMapping("/api/v1/startups")
public class StartupController {
    @Autowired
    private StartupRepository startupRepository;

    @Autowired
    private UserRepository userRepository;

    // READ Startups
    @GetMapping
    public ResponseEntity<List<Startup>> getStartups() {
        try {
            List<Startup> startups;
            startups = startupRepository.findAll();
            if (startups.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(startups, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // READ Startup by id
    @GetMapping(value = "/{id}")
    public ResponseEntity<Startup> getStartup(@PathVariable("id") Long id) {
        Optional<Startup> startupData = startupRepository.findById(id);
        if (startupData.isPresent()) {
            return new ResponseEntity<>(startupData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // CREATE Startup
    @PostMapping
    public ResponseEntity<Startup> createStartup(@RequestBody Startup startup) {
        try {
            return new ResponseEntity<>(startupRepository.save(startup), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // UPDATE Startup
    @PutMapping(value = "/{id}")
    public ResponseEntity<Startup> updateStartup(@PathVariable("id") Long id, @RequestBody Startup startup) {
        Optional<Startup> startupData = startupRepository.findById(id);
        if (startupData.isPresent()) {
            Startup _startup = startupData.get();
            _startup.setName(startup.getName());
            _startup.setDescription(startup.getDescription());
            _startup.setContent(startup.getContent());
            _startup.setGoal(startup.getGoal());
            _startup.setPublished(startup.getPublished());
            Optional<User> user = userRepository.findById(startup.getOwner().getId());
            if (user.isPresent()) {
                _startup.setOwner(user.get());
            } else {
                _startup.setOwner(null);
            }
            return new ResponseEntity<>(startupRepository.save(_startup), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // DELETE Startup by id
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<HttpStatus> deleteStartup(@PathVariable("id") Long id) {
        try {
            startupRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
