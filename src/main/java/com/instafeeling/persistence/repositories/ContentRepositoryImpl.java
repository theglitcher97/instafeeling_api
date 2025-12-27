package com.instafeeling.persistence.repositories;

import com.instafeeling.domain.models.Content;
import com.instafeeling.domain.repositories.ContentRepository;
import com.instafeeling.persistence.crud.ContentCrudRepository;
import com.instafeeling.persistence.crud.UserCrudRepository;
import com.instafeeling.persistence.entities.ContentEntity;
import com.instafeeling.persistence.entities.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@AllArgsConstructor
public class ContentRepositoryImpl implements ContentRepository {
    private final UserCrudRepository userRepository;
    private final ContentCrudRepository contentCrudRepository;

    @Override
    @Transactional
    public Content createContent(Content content) {
        UserEntity userEntity = this.userRepository.findById(content.ownerId()).get();
        ContentEntity contentEntity = new ContentEntity(
                null,
                userEntity,
                content.contentName(),
                content.contentType(),
                content.contentSize());

        this.contentCrudRepository.save(contentEntity).getId();

        return new Content(
                contentEntity.getId(),
                content.ownerId(),
                content.contentName(),
                content.contentType(),
                content.contentSize());
    }

    @Override
    @Transactional
    public void deleteContent(Long id) {
        this.contentCrudRepository.deleteById(id);
    }
}
