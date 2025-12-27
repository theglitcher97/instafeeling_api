package com.instafeeling.persistence.crud;

import com.instafeeling.persistence.entities.ContentEntity;
import org.springframework.data.repository.CrudRepository;

public interface ContentCrudRepository extends CrudRepository<ContentEntity, Long> {
}
