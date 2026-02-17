package com.instafeeling.domain.services;

import com.instafeeling.domain.ports.storage.TagsRepository;
import com.instafeeling.web.exceptions.custom.InvalidTagFormatException;
import com.instafeeling.web.exceptions.custom.TagNameTooLongException;
import com.instafeeling.web.exceptions.custom.TooManyTagsOnSingleContentException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TagsService {
    private static final byte TAGS_LIMIT = 10;
    private static final byte MAX_TAG_LENGTH = 64;
    private static final String TAG_FORMAT = "[a-zA-Z]{0,64}";
    private final TagsRepository tagsRepository;

    public void addTagsToContent(Long contentId, List<String> tags) {
        // validate total tags
        if (tags.size() > TAGS_LIMIT)
            throw new TooManyTagsOnSingleContentException("Too many tags on single content");

        if (tags.stream().anyMatch(t -> t.length() > MAX_TAG_LENGTH))
            throw new TagNameTooLongException("Tag name is too long, max "+MAX_TAG_LENGTH+" characters");

        if (tags.stream().anyMatch(t -> !t.matches(TAG_FORMAT))) {
            throw new InvalidTagFormatException("Invalid tag value, tags can only contain letters");
        }


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
