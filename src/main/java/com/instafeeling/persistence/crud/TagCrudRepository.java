package com.instafeeling.persistence.crud;

import com.instafeeling.persistence.entities.TagEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CrudTagRepository extends CrudRepository<TagEntity, Long> {
    boolean existByValue(String tag);

}
