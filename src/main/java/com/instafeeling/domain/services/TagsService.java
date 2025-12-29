package com.instafeeling.domain.services;

import com.instafeeling.domain.ports.storage.TagsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TagsService {
    private final TagsRepository tagsRepository;

    public void addTagsToContent(Long contentId, List<String> tags) {
        // validate tags value
        if (tags.size() > 5)
            throw new RuntimeException("Too many tags on single content");

        tags = tags.stream().map(t -> t.trim().toLowerCase()).collect(Collectors.toList());

        if (tags.stream().anyMatch(t -> !t.matches("[a-zA-Z]{0,64}")))
            throw new RuntimeException("Invalid tag value, tags can only contain letters");

        // normalize tags
        tags = tags.stream().map(t -> t.trim().toLowerCase()).collect(Collectors.toList());

        // create missing ones
        this.tagsRepository.ensureExistence(tags);

        // link tags to content
        this.tagsRepository.addTagsToContent(contentId, tags);
    }

    public void removeTags(List<String> tags) {
        this.tagsRepository.deleteTags(tags);
    }
}
