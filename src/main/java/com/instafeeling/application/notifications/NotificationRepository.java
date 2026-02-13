package com.instafeeling.application.notifications;

public interface NotificationRepository {
    void create(Long actorId, Long contentId, Long recipientId);
}
