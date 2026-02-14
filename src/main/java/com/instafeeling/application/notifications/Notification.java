package com.instafeeling.application.notifications;

public record Notification(
        Long id,
        Long contentId,
        String contentName,
        Long actorId,
        String actorName
) {
}
