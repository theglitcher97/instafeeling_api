package com.instafeeling.domain.dtos;

public record GenericErrorResponseDTO(
        String timestamp,
        int status,
        String error,
        String message
) {
}
