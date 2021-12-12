package com.informatorio.startups.api.repository;

import java.time.LocalDateTime;
import java.util.List;

import com.informatorio.startups.api.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByCity(String city);
    List<User> findByCreatedAtAfter(LocalDateTime fromDate);
}
