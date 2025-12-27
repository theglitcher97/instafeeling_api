package com.instafeeling.web.dtos;

public record ContentDTO(
        Long id,
        String name,
        String type,
        Long size
) {
}
