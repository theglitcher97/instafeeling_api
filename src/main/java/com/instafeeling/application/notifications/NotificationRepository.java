package com.instafeeling.application.notifications;

import java.util.List;

public interface NotificationRepository {
    void create(Long actorId, Long contentId, Long recipientId);

    List<Notification> getUserNotifications(Long userId);
}
