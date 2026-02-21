package com.instafeeling.application.use_cases;

import com.instafeeling.application.events.ContentLikedEvent;
import com.instafeeling.application.notifications.NotificationType;
import com.instafeeling.domain.services.ContentService;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@AllArgsConstructor
public class LikeContentUseCase {
    private final ContentService contentService;
    private final ApplicationEventPublisher publisher;

    @Transactional
    public void likeContent(Long userId, Long contentId) {
        Long recipientId = this.contentService.getContentOwnerId(contentId);

        // Todo: throw custom exception
        if (Objects.isNull(recipientId))
            throw new RuntimeException("Content unavailable!");

        boolean isNew = this.contentService.addLike(userId, contentId);

        if (!Objects.equals(recipientId, userId) && isNew) {
            ContentLikedEvent event = new ContentLikedEvent(userId, contentId, recipientId, NotificationType.LIKE);
            this.publisher.publishEvent(event);
        }

    }
}
