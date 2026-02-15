package com.instafeeling.domain.ports.storage;

import com.instafeeling.domain.models.Content;

import java.util.List;

public interface ContentRepository {
    Content createContent(Content content);

    void deleteContent(Long id);

    List<Content> findContent(Long userId);

    boolean validateOwnership(Long userId, Long contentId);

    boolean validateExistence(Long contentId);

    boolean createLike(Long userId, Long contentId);

     List<Content> findPopular();

    Long findContentOwnerId(Long contentId);

    boolean likeExists(Long userId, Long contentId);

    void unlike(Long userId, Long contentId);
}
