package com.instafeeling.application.notifications;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PutNotificationDTO(
        @NotNull
        NOTIFICATION_STATUS markAs
) {
}
