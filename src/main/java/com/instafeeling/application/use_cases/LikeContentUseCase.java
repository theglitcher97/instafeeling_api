package com.instafeeling.application.use_cases;

import com.instafeeling.domain.ports.storage.ContentRepository;
import com.instafeeling.domain.services.ContentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class LikeContentUseCase {
    private final ContentRepository contentRepository;
    private final ContentService contentService;

    @Transactional
    public void likeContent(Long userId, Long contentId) {
        if (!this.contentRepository.validateExistence(contentId))
            throw new RuntimeException("Content unavailable!");

        this.contentService.addLike(userId, contentId);
    }
}
