package com.instafeeling.application.events;

import com.instafeeling.application.notifications.NotificationType;

//TODO:  Add Notification Type
public record ContentLikedEvent(
        Long actorId,
        Long contentId,
        Long recipientId,
        NotificationType type
) {
}
