package com.instafeeling.domain.ports.storage;

import com.instafeeling.persistence.entities.UserEntity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public interface UserRepository {
    boolean isEmailAvailable(@NotBlank(message = "please provide an email") @Email(message = "please provide a valid email") String email);
    Long createAccount(String email, String password);
    UserEntity findUserByEmail(@NotBlank(message = "please provide an email") @Email(message = "please provide a valid email") String email);
    UserEntity findUserById(@NotNull Long userId);
    void saveAccount(UserEntity userEntity);
}
