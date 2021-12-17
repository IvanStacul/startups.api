package com.informatorio.startups.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.informatorio.startups.api.entity.Startup;
import com.informatorio.startups.api.entity.Tag;
import com.informatorio.startups.api.repository.TagRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StartupService {
    @Autowired
    private TagRepository tagRepository;

    public List<Startup> getByTag(String tagName){
        List<Startup> startups = new ArrayList<Startup>();
        Optional<Tag> tag = tagRepository.findByNameContaining(tagName);
        if (tag.isPresent()) {
            Tag tagData = tag.get();
            startups = tagData.getStartups();
        }
        return startups;
    }
}
