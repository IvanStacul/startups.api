package com.informatorio.startups.api.repository;

import java.util.List;

import com.informatorio.startups.api.entity.Startup;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StartupRepository extends JpaRepository<Startup, Long> {
    List<Startup> findByPublished(Boolean published);
}
