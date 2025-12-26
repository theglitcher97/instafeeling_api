package com.instafeeling.domain.services;

import com.instafeeling.domain.infra.PasswordManager;
import com.instafeeling.web.dtos.LoginDTO;
import com.instafeeling.web.dtos.SignUpDTO;
import com.instafeeling.domain.repositories.UserRepository;
import com.instafeeling.persistence.entities.UserEntity;
import com.instafeeling.web.utils.JwtUtils;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordManager passwordManager;
    private final JwtUtils jwtUtils;

    public String signup(SignUpDTO signUpDTO) {
        // check passwords are equal
        if (!signUpDTO.password().equals(signUpDTO.confirmPassword()))
            throw new RuntimeException("passwords don't coincide");

        // check email uniqueness
        if (!this.userRepository.isEmailAvailable(signUpDTO.email())) {
            throw new RuntimeException("This email is already in used");
        }

        // encrypt password
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(signUpDTO.email());
        userEntity.setPassword(this.passwordManager.hashPassword(signUpDTO.password()));

        // save new user
        userEntity = this.userRepository.createAccount(userEntity);

        // create token
        return this.jwtUtils.createToken(userEntity.getId());
    }

    public String login(@Valid LoginDTO loginDTO) {
        // get user by email
        UserEntity userEntity = this.userRepository.findUserByEmail(loginDTO.email());
        if (userEntity == null)
            throw new BadCredentialsException("Credential error: please check your email and password");


        // validate password
        if (!this.passwordManager.verifyPassword(loginDTO.password(), userEntity.getPassword()))
            throw new BadCredentialsException("Credential error: please check your email and password");

        // create and return token
        return this.jwtUtils.createToken(userEntity.getId());
    }
}
