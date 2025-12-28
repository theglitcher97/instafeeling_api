package com.instafeeling.domain.ports.storage;

import com.instafeeling.domain.models.Content;

import java.util.List;

public interface ContentRepository {
    Content createContent(Content content);

    void deleteContent(Long id);

    List<Content> findContent(Long userId);

    boolean validateOwnership(Long userId, Long contentId);
}
