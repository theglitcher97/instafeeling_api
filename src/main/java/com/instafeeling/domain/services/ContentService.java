package com.instafeeling.domain.services;

import com.instafeeling.domain.models.Content;
import com.instafeeling.domain.ports.storage.ContentRepository;
import com.instafeeling.domain.ports.storage.ContentStorageService;
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

    public Content createContent(Content content) {
        //validates contentType
        if (!this.contentValidator.isValidType(content.contentType()))
            throw new RuntimeException("Invalid content type");

        // validates contentSize
        if (!this.contentValidator.isValidSize(content.contentSize()))
            throw new RuntimeException("Content is too big");

        // saves metadata and creates relation between user and content
        return this.contentRepository.createContent(content);
    }

    public List<Content> getContent(Long userId) {
        return this.contentRepository.findContent(userId);
    }

    public byte[] loadUserContent(Long contentId) {
        try {
            return this.contentStorageService.loadContent(contentId.toString());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Something went wrong");
        }
    }

    public void deleteContent(Long id) {
        this.contentRepository.deleteContent(id);
    }

    public boolean addLike(Long userId, Long contentId) {
        return this.contentRepository.createLike(userId, contentId);
    }

    public Long getContentOwnerId(Long contentId) {
        return this.contentRepository.findContentOwnerId(contentId);
    }

    /**
     *
     * @param userId
     * @param contentId
     * @return true if like was removed, or false if it wasn't
     */
    public boolean unlike(Long userId, Long contentId) {
        boolean likeExists =  this.contentRepository.likeExists(userId, contentId);
        if (!likeExists) return false;
        this.contentRepository.unlike(userId, contentId);
        return true;
    }
}
