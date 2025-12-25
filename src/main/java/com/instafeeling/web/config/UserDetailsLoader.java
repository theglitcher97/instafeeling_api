package com.instafeeling.web.config;

import com.instafeeling.domain.repositories.UserRepository;
import com.instafeeling.persistence.entities.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsLoader implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = this.userRepository.findUserByEmail(username);
        if (userEntity == null)
            throw new UsernameNotFoundException("CANNOT FIND USERNAME");

        return User.builder()
                .username(userEntity.getEmail())
                .password(userEntity.getPassword())
                .roles("")
                .accountExpired(false)
                .accountLocked(false)
                .build();
    }
}
