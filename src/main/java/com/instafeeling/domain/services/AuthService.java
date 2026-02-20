package com.instafeeling.domain.services;

import com.instafeeling.domain.ports.security.PasswordManager;
import com.instafeeling.domain.ports.storage.UserRepository;
import com.instafeeling.persistence.entities.UserEntity;
import com.instafeeling.web.exceptions.custom.EmailAlreadyTakenException;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordManager passwordManager;

    public Long signup(String email, String password) {
        // check email uniqueness
        if (!this.userRepository.isEmailAvailable(email)) {
            throw new EmailAlreadyTakenException("Email is already registered");
        }

        // create new account
        return this.userRepository.createAccount(email, this.passwordManager.hashPassword(password));
    }

    public Long login(String email, String rawPassword) {
        // get user by email
        UserEntity userEntity = this.userRepository.findUserByEmail(email);
        if (userEntity == null)
            throw new BadCredentialsException("Credential error: please check your email and password");

        // validate password
        if (!this.passwordManager.verifyPassword(rawPassword, userEntity.getPassword()))
            throw new BadCredentialsException("Credential error: please check your email and password");

        // create and return token
        return userEntity.getId();
    }
}
