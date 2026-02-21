package com.instafeeling.application.use_cases;

import com.instafeeling.application.notifications.Notification;
import com.instafeeling.application.notifications.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@AllArgsConstructor
public class NotificationUseCases {
    private final NotificationService notificationService;

    @Transactional
    public List<Notification> getUserNotifications(Long userId) {
        return this.notificationService.getUserNotifications(userId);
    }

    @Transactional
    public void markAsUnread(Long userId, Long id) {
        this.notificationService.markAsUnread(userId, id);
    }

    @Transactional
    public void maskAsReadUseCase(Long userId, Long id) {
        this.notificationService.markAsRead(userId, id);
    }

    @Transactional
    public void markAllAsRead(Long userId) {
        this.notificationService.markAllAsRead(userId);
    }
}
