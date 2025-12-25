package com.instafeeling.domain.services;

import com.instafeeling.domain.repositories.UserRepository;
import com.instafeeling.persistence.entities.UserEntity;
import com.instafeeling.web.config.SecurityConfig;
import com.instafeeling.web.dtos.UpdateEmailDTO;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AccountManagerService {
    private final SecurityConfig securityConfig;
    private final UserRepository userRepository;

    public void updateEmail(@Valid UpdateEmailDTO updateEmailDTO) {
        // validate this person password is correct
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity userEntity = this.userRepository.findUserByEmail(email);

        if (!this.securityConfig.passwordEncoder().matches(updateEmailDTO.password(), userEntity.getPassword()))
            throw new BadCredentialsException("Credential error: please check your password");

        // validate the email is free
        if (!this.userRepository.isEmailAvailable(updateEmailDTO.email()))
            throw new RuntimeException("This email is already in used");

        // update email
        userEntity.setEmail(updateEmailDTO.email());
        this.userRepository.saveAccount(userEntity);
    }
}
