package com.instafeeling.persistence.mappers;

import com.instafeeling.domain.models.Content;
import com.instafeeling.persistence.entities.ContentEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ContentEntityMapper {
    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "contentName", target = "name"),
            @Mapping(source = "contentType", target = "type"),
            @Mapping(source = "contentSize", target = "size"),
            @Mapping(target = "userEntity", ignore = true),
    })
    ContentEntity toContentEntity(Content content);

    @InheritInverseConfiguration
    @Mapping(source = "userEntity.id", target = "ownerId")
    Content toContent(ContentEntity contentEntity);
    List<Content> toContent(List<ContentEntity> contentEntities);
}
