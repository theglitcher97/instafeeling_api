package com.instafeeling.persistence.repositories;

import com.instafeeling.application.notifications.NotificationType;
import com.instafeeling.persistence.entities.NotificationEntity;
import com.instafeeling.application.notifications.Notification;
import com.instafeeling.application.notifications.NotificationRepository;
import com.instafeeling.persistence.crud.NotificationCrudRepository;
import jakarta.validation.ConstraintViolationException;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class NotificationRepositoryImpl implements NotificationRepository {
    private final NotificationCrudRepository notificationCrudRepository;

    @Override
    public void create(Long actorId, Long contentId, Long recipientId, NotificationType type) {
        try {
            this.notificationCrudRepository.save(new NotificationEntity(recipientId, contentId, actorId, type));
        } catch (ConstraintViolationException | DataIntegrityViolationException e){
            System.out.println(e.getMessage());
        } catch (RuntimeException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<Notification> getUserNotifications(Long userId) {
        return this.notificationCrudRepository.findUserNotifications(userId);
    }

    @Override
    public void markAsUnread(Long userId, Long id) {
        this.notificationCrudRepository.markAs(userId, id, false);
    }

    @Override
    public void markAsRead(Long userId, Long id) {
        this.notificationCrudRepository.markAs(userId, id, true);
    }

    @Override
    public void deleteNotification(Long actorId, Long contentId, Long recipientId) {
        this.notificationCrudRepository.deleteByActorIdAndContentIdAndRecipientId(actorId, contentId, recipientId);
    }

    @Override
    public void markAllAsRead(Long userId) {
        this.notificationCrudRepository.markAllAsRead(userId);
    }
}
