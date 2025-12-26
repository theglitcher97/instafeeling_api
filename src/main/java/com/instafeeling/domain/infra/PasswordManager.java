package com.instafeeling.domain.infra;

public interface PasswordManager {
    boolean verifyPassword(String rawPassword, String hashedPassword);
    String hashPassword(String rawPassword);
}
