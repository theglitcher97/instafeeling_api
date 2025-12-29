package com.instafeeling.application.use_cases;

import com.instafeeling.domain.models.Content;
import com.instafeeling.domain.ports.storage.ContentStorageService;
import com.instafeeling.domain.services.ContentService;
import com.instafeeling.persistence.mappers.ContentMapper;
import com.instafeeling.web.dtos.ContentDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class UploadContentUseCase {
    private final ContentService contentService;
    private final ContentStorageService contentStorageService;
    private final ContentMapper contentMapper;

    public ContentDTO uploadContent(Long userId, String contentName, String contentType, Long contentSize, byte[] contentBytes){
        Content content = new Content(null, userId,  contentName, contentType, contentSize);
        // saves content meta-data and creates relation with owner
        content = this.contentService.createContent(content);


        try {
            // stores the files
            this.contentStorageService.storeContent(content.id().toString(), contentBytes);
            return this.contentMapper.toContentDTO(content);
        }catch (RuntimeException | IOException e){
            this.contentService.deleteContent(content.id());
            throw new RuntimeException("Something went wrong");
        }
    }
}
