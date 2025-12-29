package com.instafeeling.persistence.crud;

import com.instafeeling.persistence.entities.TagEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface TagCrudRepository extends CrudRepository<TagEntity, Long> {
    Optional<TagEntity> findByValue(String tag);

    List<TagEntity> findAllByValueIn(List<String> tags);

    void deleteAllByValueIn(List<String> tags);
}
