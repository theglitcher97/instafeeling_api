package com.instafeeling.persistence.crud;


import com.instafeeling.application.entities.NotificationEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationCrudRepository extends CrudRepository<NotificationEntity, Long> {
}
