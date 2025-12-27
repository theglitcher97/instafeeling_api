package com.instafeeling.persistence.mappers;

import com.instafeeling.domain.models.Content;
import com.instafeeling.web.dtos.ContentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ContentMapper {
    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "contentName", target = "name"),
            @Mapping(source = "contentType", target = "type"),
            @Mapping(source = "contentSize", target = "size"),
    })
    ContentDTO toContentDTO(Content content);
    List<ContentDTO> toContentDTO(List<Content> contents);
}
