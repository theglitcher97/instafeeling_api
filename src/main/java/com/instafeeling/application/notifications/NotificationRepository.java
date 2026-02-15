package com.instafeeling.application.notifications;

import java.util.List;

public interface NotificationRepository {
    void create(Long actorId, Long contentId, Long recipientId);

    List<Notification> getUserNotifications(Long userId);

    void markAsUnread(Long userId, Long id);

    void markAsRead(Long userId, Long id);

    void deleteNotification(Long actorId, Long contentId, Long recipientId);
}
