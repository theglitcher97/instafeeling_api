package com.instafeeling.domain.services;

import com.instafeeling.domain.dtos.SignUpDTO;
import com.instafeeling.domain.repositories.UserRepository;
import com.instafeeling.persistence.entities.UserEntity;
import com.instafeeling.web.config.SecurePasswordEncoder;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final SecurePasswordEncoder passwordEncoder;

    public void signup(@Valid SignUpDTO signUpDTO) {
        // check email uniqueness
        if (!this.userRepository.isEmailAvailable(signUpDTO.email())) {
            throw new RuntimeException("This email is already in used");
        }

        // encrypt password
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(signUpDTO.email());
        userEntity.setPassword(this.passwordEncoder.passwordEncoder().encode(signUpDTO.password()));

        // save new user
        this.userRepository.createAcccount(userEntity);

        // create token
    }
}
