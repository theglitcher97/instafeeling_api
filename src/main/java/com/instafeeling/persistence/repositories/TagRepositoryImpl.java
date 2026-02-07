package com.instafeeling.persistence.repositories;

import com.instafeeling.domain.ports.storage.TagsRepository;
import com.instafeeling.persistence.crud.ContentCrudRepository;
import com.instafeeling.persistence.crud.ContentTagCrudRepository;
import com.instafeeling.persistence.crud.TagCrudRepository;
import com.instafeeling.persistence.entities.ContentEntity;
import com.instafeeling.persistence.entities.ContentTagsEntity;
import com.instafeeling.persistence.entities.TagEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class TagRepositoryImpl implements TagsRepository {
    private final TagCrudRepository tagCrudRepository;
    private final ContentCrudRepository contentCrudRepository;
    private final ContentTagCrudRepository contentTagCrudRepository;

    @Override
    public void ensureExistence(List<String> tags) {
        List<String> missingTags = new ArrayList<>();
        tags.forEach(t -> {
            if (this.tagCrudRepository.findByValue(t).isEmpty())
                missingTags.add(t);
        });

        if (!missingTags.isEmpty()) {
            List<TagEntity> missingTagEntities = missingTags.stream().map(t -> {
                TagEntity tagEntity = new TagEntity();
                tagEntity.setValue(t);
                return tagEntity;
            }).collect(Collectors.toList());
            this.tagCrudRepository.saveAll(missingTagEntities);
        }
    }

    @Override
    public void addTagsToContent(Long id, List<String> tags) {
        ContentEntity contentEntity = this.contentCrudRepository.findById(id).get();
        List<TagEntity> tagEntities = this.tagCrudRepository.findAllByValueIn(tags);

        List<ContentTagsEntity> contentTagsEntities = new ArrayList<>();
        for (TagEntity tag : tagEntities) {
            contentTagsEntities.add(new ContentTagsEntity(contentEntity, tag));
        }

        this.contentTagCrudRepository.saveAll(contentTagsEntities);
    }

    @Override
    @Transactional
    public void deleteTags(List<String> tags) {
        this.tagCrudRepository.deleteAllByValueIn(tags);
    }
}
