package com.instafeeling.application.listeners;

import com.instafeeling.application.events.ContentLikedEvent;
import com.instafeeling.application.notifications.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@AllArgsConstructor
public class ContentLikedEventListener {
    private final NotificationService notificationService;

    // because this method will be listening to an event publish during a transaction,
    // if the transaction fails, this should no be call, that's why
    // we use "AFTER_COMMIT"
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void contentLikedEventListener(ContentLikedEvent event){
        this.notificationService.createNotificationOnEvent(event.actorId(), event.contentId(), event.recipientId()  );
    }
}
