package com.informatorio.startups.api.controller;

import java.util.List;
import java.util.Optional;

import com.informatorio.startups.api.entity.Startup;
import com.informatorio.startups.api.entity.Vote;
import com.informatorio.startups.api.repository.StartupRepository;
import com.informatorio.startups.api.repository.VoteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/votes")
public class VoteController {

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private StartupRepository startupRepository;

    // CREATE vote
    @PostMapping
    public ResponseEntity<Vote> createVote(@RequestBody Vote vote) {
        try {
            Optional<Startup> startup = startupRepository.findById(vote.getStartupId());

            if (startup.isPresent()) {
                Startup startupData = startup.get();
                startupData.setVotes(startupData.getVotes() + 1);
                startupRepository.save(startupData);
            }

            return new ResponseEntity<>(voteRepository.save(vote), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    // GET votes by user
    @GetMapping("/{userId}")
    public ResponseEntity<List<Vote>> getVotesByUser(@PathVariable("userId") Long userId) {
        try {
            return new ResponseEntity<>(voteRepository.findByUserId(userId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
