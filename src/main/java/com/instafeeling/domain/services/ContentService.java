package com.instafeeling.domain.services;

import com.instafeeling.domain.ports.storage.ContentStorageService;
import com.instafeeling.domain.models.Content;
import com.instafeeling.domain.ports.storage.ContentRepository;
import com.instafeeling.domain.validators.ContentValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class ContentService {
    private final ContentValidator contentValidator;
    private final ContentRepository contentRepository;
    private final ContentStorageService contentStorageService;

    public Long createContent(Long userId, String contentName, String contentType, Long contentSize, byte[] contentBytes) {
        Content content = new Content(null, userId,  contentName, contentType, contentSize);

        //validates contentType
        if (!this.contentValidator.isValidType(content.contentType()))
            throw new RuntimeException("Invalid content type");

        // validates contentSize
        if (!this.contentValidator.isValidSize(content.contentSize()))
            throw new RuntimeException("Content is too big");

        // saves metadata and creates relation between user and content
        content = this.contentRepository.createContent(content);

        try {
            // stores the files
            this.contentStorageService.storeContent(content.id().toString(), contentBytes);
            return content.id();
        }catch (RuntimeException | IOException e){
            this.contentRepository.deleteContent(content.id());
            throw new RuntimeException("Something went wrong");
        }
    }

    public List<Content> getContent(Long userId) {
        return this.contentRepository.findContent(userId);
    }

    public byte[] loadUserContent(Long userId, Long contentId) {
        if (!this.contentRepository.validateOwnership(userId, contentId))
            throw new RuntimeException("You are not authorized!");

        try {
            return this.contentStorageService.loadContent(contentId.toString());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Something went wrong");
        }
    }
}
