package com.instafeeling.web.exceptions.custom;

public class TagNameTooLongException extends RuntimeException {
    public TagNameTooLongException(String message) {
        super(message);
    }
}
