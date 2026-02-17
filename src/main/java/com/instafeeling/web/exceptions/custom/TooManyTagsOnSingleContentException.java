package com.instafeeling.web.exceptions.custom;

public class TooManyTagsOnSingleContentException extends RuntimeException {
    public TooManyTagsOnSingleContentException(String message) {
        super(message);
    }
}
