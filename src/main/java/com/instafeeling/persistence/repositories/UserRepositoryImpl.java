package com.instafeeling.persistence.repositories;

import com.instafeeling.domain.repositories.UserRepository;
import com.instafeeling.persistence.crud.UserCrudRepository;
import com.instafeeling.persistence.entities.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@AllArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final UserCrudRepository userCrudRepository;

    @Override
    public boolean isEmailAvailable(String email) {
        return !this.userCrudRepository.existsByEmail(email);
    }

    @Override
    @Transactional
    public void createAccount(UserEntity userEntity) {
        this.userCrudRepository.save(userEntity);
    }

    @Override
    public UserEntity findUserByEmail(String email) {
        return this.userCrudRepository.findByEmail(email).orElse(null);
    }
}
