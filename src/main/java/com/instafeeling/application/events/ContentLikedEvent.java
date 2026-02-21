package com.instafeeling.application.events;

import com.instafeeling.application.notifications.NotificationType;

public record ContentLikedEvent(
        Long actorId,
        Long contentId,
        Long recipientId,
        NotificationType type
) {
}
