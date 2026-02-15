package com.instafeeling.persistence.repositories;

import com.instafeeling.domain.models.Content;
import com.instafeeling.domain.ports.storage.ContentRepository;
import com.instafeeling.persistence.crud.ContentCrudRepository;
import com.instafeeling.persistence.crud.LikeCrudRepository;
import com.instafeeling.persistence.crud.TagCrudRepository;
import com.instafeeling.persistence.crud.UserCrudRepository;
import com.instafeeling.persistence.entities.ContentEntity;
import com.instafeeling.persistence.entities.LikeEntity;
import com.instafeeling.persistence.entities.TagEntity;
import com.instafeeling.persistence.entities.UserEntity;
import com.instafeeling.persistence.entities.embeddedIDs.LikeEntityID;
import com.instafeeling.persistence.mappers.ContentEntityMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class ContentRepositoryImpl implements ContentRepository {
    private final UserCrudRepository userCrudRepository;
    private final ContentCrudRepository contentCrudRepository;
    private final LikeCrudRepository likeCrudRepository;
    private final ContentEntityMapper contentEntityMapper;
    private final TagCrudRepository tagCrudRepository;

    @Override
    public Content createContent(Content content) {
        UserEntity userEntity = this.userCrudRepository.findById(content.ownerId()).get();
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

    @Override
    public boolean validateExistence(Long contentId) {
        return this.contentCrudRepository.findById(contentId).isPresent();
    }

    @Override
    public void createLike(Long userId, Long contentId) {
        UserEntity userEntity = this.userCrudRepository.findById(userId).get();
        ContentEntity contentEntity = this.contentCrudRepository.findById(contentId).get();
        LikeEntity likeEntity = new LikeEntity(userEntity, contentEntity);
        this.likeCrudRepository.save(likeEntity);
    }

    @Override
    public List<Content> findPopular() {
        List<TagEntity> tagEntities = this.tagCrudRepository.findPopularTags();
        List<String> tagValues = tagEntities.stream().map(TagEntity::getValue).collect(Collectors.toList());
        return this.contentEntityMapper.toContent(this.contentCrudRepository.findPopularWithTags(tagValues));
    }

    @Override
    public Long findContentOwnerId(Long contentId) {
        return this.contentCrudRepository.findContentOwnerId(contentId);
    }

    @Override
    public boolean likeExists(Long userId, Long contentId) {
        return this.likeCrudRepository.existsById(new LikeEntityID(userId, contentId));
    }

    @Override
    public void unlike(Long userId, Long contentId) {
        this.likeCrudRepository.deleteById(new LikeEntityID(userId, contentId));
    }
}
