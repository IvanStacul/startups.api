package com.informatorio.startups.api.repository;

import java.util.Optional;

import com.informatorio.startups.api.entity.Tag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findByNameContaining(String name);
}
