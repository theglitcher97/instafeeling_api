package com.instafeeling.domain.models;

public record Content(
        Long id,
        Long ownerId,
        String contentName,
        String contentType,
        Long contentSize
) {
}
