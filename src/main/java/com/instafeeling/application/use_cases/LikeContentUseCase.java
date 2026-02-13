package com.instafeeling.application.use_cases;

import com.instafeeling.application.events.ContentLikedEvent;
import com.instafeeling.domain.ports.storage.ContentRepository;
import com.instafeeling.domain.services.ContentService;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.Objects;

@Service
@AllArgsConstructor
public class LikeContentUseCase {
    private final ContentRepository contentRepository;
    private final ContentService contentService;
    private final ApplicationEventPublisher publisher;

    @Transactional
    public void likeContent(Long userId, Long contentId) {
        Long recipientId = this.contentService.getContentOwnerId(contentId);

        if (Objects.isNull(recipientId))
            throw new RuntimeException("Content unavailable!");

        boolean isNew = this.contentService.addLike(userId, contentId);

        if (!Objects.equals(recipientId, userId) && isNew) {
            // emit notification event
            System.out.println("Publishing contentLikedEEvent");
            ContentLikedEvent event = new ContentLikedEvent(userId, contentId, recipientId);
            this.publisher.publishEvent(event);
        }
        System.out.println("Tx active: " + TransactionSynchronizationManager.isActualTransactionActive());
        System.out.println("Sync active: " + TransactionSynchronizationManager.isSynchronizationActive());

    }
}
