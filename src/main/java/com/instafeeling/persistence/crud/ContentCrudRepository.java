package com.instafeeling.persistence.crud;

import com.instafeeling.persistence.entities.ContentEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContentCrudRepository extends CrudRepository<ContentEntity, Long> {
    @Query("Select ce from ContentEntity ce Where ce.userEntity.id = :userId")
    List<ContentEntity> findAllByUserId(@Param("userId") Long userId);
}
