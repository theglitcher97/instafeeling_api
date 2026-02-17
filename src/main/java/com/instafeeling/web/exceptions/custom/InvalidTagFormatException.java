package com.instafeeling.web.exceptions.custom;

public class InvalidTagFormatException extends RuntimeException {
    public InvalidTagFormatException(String msg) {
        super(msg);
    }
}
