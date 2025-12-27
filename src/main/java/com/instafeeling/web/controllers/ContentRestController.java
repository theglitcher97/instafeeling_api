package com.instafeeling.web.controllers;

import com.instafeeling.domain.models.Content;
import com.instafeeling.domain.services.ContentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping(value = "/v1/content")
@AllArgsConstructor
public class ContentRestController {
    private final ContentService contentService;

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
}
