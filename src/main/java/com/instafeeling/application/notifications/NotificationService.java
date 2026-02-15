package com.instafeeling.application.notifications;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;

    public void createNotificationOnEvent(Long actorId, Long contentId, Long recipientId) {
        this.notificationRepository.create(actorId, contentId, recipientId);
    }

    public List<Notification> getUserNotifications(Long userId) {
        return this.notificationRepository.getUserNotifications(userId);
    }

    public void markAsUnread(Long userId, Long id) {
        this.notificationRepository.markAsUnread(userId, id);
    }

    public void markAsRead(Long userId, Long id) {
        this.notificationRepository.markAsRead(userId, id);
    }

    public void removeNotificationOnEvent(Long actorId, Long contentId, Long recipientId) {
        this.notificationRepository.deleteNotification(actorId, contentId, recipientId);
    }
}
