package com.instafeeling.domain.services;

import com.instafeeling.domain.infra.PasswordManager;
import com.instafeeling.domain.repositories.UserRepository;
import com.instafeeling.persistence.entities.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AccountManagerService {
    private final PasswordManager passwordManager;
    private final UserRepository userRepository;

    public void updateEmail(Long userId, String newEmail, String currentPassword) {
        UserEntity userEntity = this.userRepository.findUserById(userId);

        // validate this person password is correct
        if (!this.passwordManager.verifyPassword(currentPassword, userEntity.getPassword()))
            throw new BadCredentialsException("Credential error: please check your password");

        // validate the email is free
        if (!this.userRepository.isEmailAvailable(newEmail))
            throw new RuntimeException("This email is already in used");

        // update email
        userEntity.setEmail(newEmail);
        this.userRepository.saveAccount(userEntity);
    }
}
