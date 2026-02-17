package com.instafeeling.web.dtos;

public record GenericErrorResponse(
        int status,
        String error,
        String message
) {
}
