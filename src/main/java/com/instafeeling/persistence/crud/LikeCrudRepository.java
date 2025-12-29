package com.instafeeling.persistence.crud;

import com.instafeeling.persistence.entities.LikeEntity;
import org.springframework.data.repository.CrudRepository;

public interface LikeCrudRepository extends CrudRepository<LikeEntity, Long> {
}
