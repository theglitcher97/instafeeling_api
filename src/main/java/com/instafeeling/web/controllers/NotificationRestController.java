package com.instafeeling.web.controllers;

import com.instafeeling.application.notifications.Notification;
import com.instafeeling.application.notifications.PutNotificationDTO;
import com.instafeeling.application.use_cases.NotificationUseCases;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/notifications")
@AllArgsConstructor
public class NotificationRestController {
    private final NotificationUseCases notificationUseCases;

    @GetMapping
    public ResponseEntity<List<Notification>> getUserNotifications(){
        Long userId = Long.parseLong((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        List<Notification> notifications = this.notificationUseCases.getUserNotifications(userId);
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateNotificationStatus(@Valid @RequestBody PutNotificationDTO notificationDTO, @PathVariable Long id) {
        Long userId = Long.parseLong((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        switch (notificationDTO.markAs()) {
            case UNREAD -> this.notificationUseCases.markAsUnread(userId, id);
            default -> this.notificationUseCases.maskAsReadUseCase(userId, id);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
