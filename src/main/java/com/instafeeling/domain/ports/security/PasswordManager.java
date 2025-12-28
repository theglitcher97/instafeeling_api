package com.instafeeling.domain.ports.security;

public interface PasswordManager {
    boolean verifyPassword(String rawPassword, String hashedPassword);
    String hashPassword(String rawPassword);
}
