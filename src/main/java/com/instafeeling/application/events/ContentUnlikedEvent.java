package com.instafeeling.application.events;

public record ContentUnlikedEvent(
        Long actorId,
        Long contentId,
        Long recipientId
) {
}
