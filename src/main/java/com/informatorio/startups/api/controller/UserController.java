package com.informatorio.startups.api.controller;

import java.util.List;
import java.util.Optional;

import com.informatorio.startups.api.entity.User;
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
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // READ users
    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        try {
            List<User> users = userRepository.findAll();

            if (users.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // READ users by id
    @GetMapping(value = "/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") Long id) {
        Optional<User> userData = userRepository.findById(id);
        if (userData.isPresent()) {
            return new ResponseEntity<>(userData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // CREATE users
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        try {
            return new ResponseEntity<>(userRepository.save(user), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // UPDATE users
    @PutMapping(value = "/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") Long id, @RequestBody User user) {
        Optional<User> userData = userRepository.findById(id);
        if (userData.isPresent()) {
            User _user = userData.get();
            _user.setName(user.getName());
            _user.setLastName(user.getLastName());
            _user.setEmail(user.getEmail());
            _user.setProvince(user.getProvince());
            _user.setCountry(user.getCountry());
            _user.setCity(user.getCity());
            _user.setPassword(user.getPassword());
            _user.setRole(user.getRole());
            return new ResponseEntity<>(userRepository.save(_user), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // DELETE users by id
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<HttpStatus> deleteGenre(@PathVariable("id") Long id) {
        try {
            userRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
