package com.instafeeling.web.config;

import com.instafeeling.domain.ports.security.PasswordManager;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordManagerImpl implements PasswordManager {
    @Override
    public boolean verifyPassword(String rawPassword, String hashedPassword) {
        return this.passwordEncoder().matches(rawPassword, hashedPassword);
    }

    @Override
    public String hashPassword(String rawPassword) {
        return this.passwordEncoder().encode(rawPassword);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
