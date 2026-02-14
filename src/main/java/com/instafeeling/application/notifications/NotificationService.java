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
}
