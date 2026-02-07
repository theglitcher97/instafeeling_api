package com.instafeeling.persistence.crud;

import com.instafeeling.persistence.entities.TagEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface TagCrudRepository extends CrudRepository<TagEntity, Long> {
    Optional<TagEntity> findByValue(String tag);

    List<TagEntity> findAllByValueIn(List<String> tags);

    void deleteAllByValueIn(List<String> tags);

    @Query(value = "SELECT t FROM TagEntity t where t.id in " +
            "(SELECT ct.id.tagId FROM ContentTagsEntity ct " +
            "GROUP BY ct.id.tagId " +
            "ORDER BY COUNT(ct.id.tagId) " +
            "LIMIT 5)")
    List<TagEntity> findPopularTags();
}
