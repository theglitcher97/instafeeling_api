package com.instafeeling.persistence.repositories;

import com.instafeeling.domain.models.Content;
import com.instafeeling.domain.ports.storage.ContentRepository;
import com.instafeeling.persistence.crud.ContentCrudRepository;
import com.instafeeling.persistence.crud.UserCrudRepository;
import com.instafeeling.persistence.entities.ContentEntity;
import com.instafeeling.persistence.entities.UserEntity;
import com.instafeeling.persistence.mappers.ContentEntityMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@AllArgsConstructor
public class ContentRepositoryImpl implements ContentRepository {
    private final UserCrudRepository userRepository;
    private final ContentCrudRepository contentCrudRepository;
    private final ContentEntityMapper contentEntityMapper;

    @Override
    @Transactional
    public Content createContent(Content content) {
        UserEntity userEntity = this.userRepository.findById(content.ownerId()).get();
        ContentEntity contentEntity = this.contentEntityMapper.toContentEntity(content);
        contentEntity.setUserEntity(userEntity);

        this.contentCrudRepository.save(contentEntity);
        return this.contentEntityMapper.toContent(contentEntity);
    }

    @Override
    @Transactional
    public void deleteContent(Long id) {
        this.contentCrudRepository.deleteById(id);
    }

    @Override
    public List<Content> findContent(Long userId) {
        return this.contentEntityMapper.toContent(this.contentCrudRepository.findAllByUserId(userId));
    }

    @Override
    public boolean validateOwnership(Long userId, Long contentId) {
        return this.contentCrudRepository.findByIdAndUserId(contentId, userId).isPresent();
    }
}
