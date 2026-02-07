package com.instafeeling.persistence.crud;

import com.instafeeling.domain.models.Content;
import com.instafeeling.persistence.entities.ContentEntity;
import com.instafeeling.persistence.entities.TagEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ContentCrudRepository extends CrudRepository<ContentEntity, Long> {
    @Query("Select ce from ContentEntity ce Where ce.userEntity.id = :userId")
    List<ContentEntity> findAllByUserId(@Param("userId") Long userId);

    @Query("Select cen From ContentEntity cen where cen.id = :contentId and cen.userEntity.id = :userId")
    Optional<ContentEntity> findByIdAndUserId(@Param("contentId") Long contentId, @Param("userId") Long userId);

    @Query("SELECT ce " +
            "FROM ContentEntity ce " +
            "JOIN ce.tags t " +
            "WHERE t.tagEntity.value in :tagValues " +
            "GROUP BY ce " +
            "ORDER BY COUNT(ce)")
    List<ContentEntity> findPopularWithTags(@Param("tagValues") List<String> tagValues);
}
