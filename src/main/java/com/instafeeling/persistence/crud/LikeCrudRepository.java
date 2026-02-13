package com.instafeeling.persistence.crud;

import com.instafeeling.persistence.entities.ContentEntity;
import com.instafeeling.persistence.entities.LikeEntity;
import com.instafeeling.persistence.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface LikeCrudRepository extends CrudRepository<LikeEntity, Long> {
    boolean existsByUserEntityAndContentEntity(UserEntity userEntity, ContentEntity contentEntity);
}
