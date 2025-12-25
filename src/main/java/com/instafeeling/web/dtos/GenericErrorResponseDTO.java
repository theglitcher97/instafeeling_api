package com.instafeeling.web.dtos;

public record GenericErrorResponseDTO(
        String timestamp,
        int status,
        String error,
        String message
) {
}
