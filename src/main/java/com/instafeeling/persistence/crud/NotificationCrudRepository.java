package com.instafeeling.persistence.crud;


import com.instafeeling.application.entities.NotificationEntity;
import com.instafeeling.application.notifications.Notification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationCrudRepository extends CrudRepository<NotificationEntity, Long> {

    @Query("SELECT new com.instafeeling.application.notifications.Notification(ne.id, ne.contentId, ne.content.name, ne.actorId, ne.actor.email) " +
            "FROM NotificationEntity ne " +
            "WHERE ne.recipientId = :recipientId AND ne.read = false")
    List<Notification> findUserNotifications(@Param("recipientId") Long userId);
}
