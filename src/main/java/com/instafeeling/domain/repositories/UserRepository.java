package com.instafeeling.domain.repositories;

import com.instafeeling.persistence.entities.UserEntity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public interface UserRepository {
    boolean isEmailAvailable(@NotBlank(message = "please provide an email") @Email(message = "please provide a valid email") String email);

    void createAcccount(UserEntity userEntity);
}
