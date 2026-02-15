package com.instafeeling.web.config;

import com.instafeeling.domain.ports.security.PasswordManager;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PasswordManagerImpl implements PasswordManager {
    private PasswordHasher pm;

    @Override
    public boolean verifyPassword(String rawPassword, String hashedPassword) {
        return this.pm.passwordEncoder().matches(rawPassword, hashedPassword);
    }

    @Override
    public String hashPassword(String rawPassword) {
        return this.pm.passwordEncoder().encode(rawPassword);
    }
}
