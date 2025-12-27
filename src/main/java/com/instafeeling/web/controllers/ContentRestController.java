package com.instafeeling.web.controllers;

import com.instafeeling.domain.models.Content;
import com.instafeeling.domain.services.ContentService;
import com.instafeeling.persistence.mappers.ContentMapper;
import com.instafeeling.web.dtos.ContentDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping(value = "/v1/content")
@AllArgsConstructor
public class ContentRestController {
    private final ContentService contentService;
    private final ContentMapper contentMapper;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Long> createContent(@RequestParam("file") MultipartFile file) throws IOException {
        Long userId = Long.parseLong((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        if (!file.isEmpty()) {
            String contentName = file.getOriginalFilename();
            String contentType = file.getContentType();
            Long contentSize = file.getSize();
            byte[] contentBytes = file.getBytes();

            Long contentId = this.contentService.createContent(userId, contentName, contentType, contentSize, contentBytes);
            return new ResponseEntity<>(contentId, HttpStatus.CREATED);
        }else{
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<ContentDTO>> getContentList(){
        Long userId = Long.parseLong((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        List<Content> contentList = this.contentService.getContent(userId);
        return ResponseEntity.ok().body(this.contentMapper.toContentDTO(contentList));
    }
}
