package com.instafeeling.persistence.crud;

import com.instafeeling.persistence.entities.ContentTagsEntity;
import org.springframework.data.repository.CrudRepository;

public interface ContentTagCrudRepository extends CrudRepository<ContentTagsEntity, Long> {
}
