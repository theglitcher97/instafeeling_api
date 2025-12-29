package com.instafeeling.web.controllers;

import com.instafeeling.application.use_cases.UploadContentUseCase;
import com.instafeeling.domain.models.Content;
import com.instafeeling.domain.services.ContentService;
import com.instafeeling.persistence.mappers.ContentMapper;
import com.instafeeling.web.dtos.ContentDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(value = "/v1/content")
@AllArgsConstructor
public class ContentRestController {
    private final ContentService contentService;
    private final ContentMapper contentMapper;
    private final UploadContentUseCase uploadContentUseCase;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ContentDTO> createContent(@RequestParam("file") MultipartFile file, @RequestParam("tags") String tags) throws IOException {
        Long userId = Long.parseLong((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        if (!file.isEmpty()) {
            String contentName = file.getOriginalFilename();
            String contentType = file.getContentType();
            Long contentSize = file.getSize();
            byte[] contentBytes = file.getBytes();
            List<String> tagsList = Arrays.stream(tags.split(",")).toList();

            ContentDTO contentDTO = this.uploadContentUseCase.uploadContent(userId, contentName, contentType, contentSize, contentBytes, tagsList);
            return new ResponseEntity<>(contentDTO, HttpStatus.CREATED);
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

    @GetMapping(value = "/{id}", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
    public ResponseEntity<byte[]> getContent(@PathVariable("id") Long contentId){
        Long userId = Long.parseLong((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return ResponseEntity.ok().body(this.contentService.loadUserContent(userId, contentId));
    }
}
