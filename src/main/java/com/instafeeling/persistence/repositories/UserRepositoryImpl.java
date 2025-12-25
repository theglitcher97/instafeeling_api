package com.instafeeling.persistence.repositories;

import com.instafeeling.domain.repositories.UserRepository;
import com.instafeeling.persistence.crud.UserCrudRepository;
import com.instafeeling.persistence.entities.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@AllArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final UserCrudRepository userCrudRepository;

    @Override
    public boolean isEmailAvailable(String email) {
        return !this.userCrudRepository.existsByEmail(email);
    }

    @Override
    @Transactional
    public UserEntity createAccount(UserEntity userEntity) {
        return this.userCrudRepository.save(userEntity);
    }

    @Override
    public UserEntity findUserByEmail(String email) {
        return this.userCrudRepository.findByEmail(email).orElse(null);
    }

    @Override
    public UserEntity findUserById(Long userId) {
        return this.userCrudRepository.findById(userId).orElse(null);
    }

    @Override
    @Transactional
    public void saveAccount(UserEntity userEntity) {
        this.userCrudRepository.save(userEntity);
    }
}
