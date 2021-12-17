package com.informatorio.startups.api.repository;

import java.util.List;

import com.informatorio.startups.api.entity.Vote;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    List<Vote> findByUserId(Long userId);
}
