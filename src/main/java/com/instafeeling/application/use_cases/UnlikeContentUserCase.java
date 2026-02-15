package com.instafeeling.application.use_cases;

import com.instafeeling.application.events.ContentUnlikedEvent;
import com.instafeeling.domain.services.ContentService;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Component
@AllArgsConstructor
public class UnlikeContentUserCase {
    private final ContentService contentService;
    private final ApplicationEventPublisher publisher;

    @Transactional
    public void unlikeContent(Long userId, Long contentId) {
        Long recipientId = this.contentService.getContentOwnerId(contentId);

        // Todo: create custom exception
        if (Objects.isNull(recipientId))
            throw new RuntimeException("Content unavailable!");

        boolean wasRemoved = this.contentService.unlike(userId, contentId);

        // if current user is not the owner and it was removed
        // we need to remove the notification
        if (!Objects.equals(recipientId, userId) && wasRemoved) {
            ContentUnlikedEvent event = new ContentUnlikedEvent(userId, contentId, recipientId);
            this.publisher.publishEvent(event);
        }
    }
}
