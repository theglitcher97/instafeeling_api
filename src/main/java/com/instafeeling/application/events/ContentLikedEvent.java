package com.instafeeling.application.events;

public record ContentLikedEvent(
        Long actorId,
        Long contentId,
        Long recipientId
) {
}
