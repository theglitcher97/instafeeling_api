package com.instafeeling.domain.validators;

import org.springframework.stereotype.Component;

@Component
public class ContentValidator {
    // 1 MB = 1024KB * 1024 Bytes
    private static final Long ONE_MB_IN_BYTES = 1024 * 1024L;

    public boolean isValidType(String rawType){
        String[] typeParts = rawType.split("/");
        return typeParts[0].equals("image");
    }

    public boolean isValidSize(Long imageSizeInBytes){
        return imageSizeInBytes < (ONE_MB_IN_BYTES * 12);
    }
}
