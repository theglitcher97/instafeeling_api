package com.instafeeling.web.controllers;

import com.instafeeling.application.use_cases.*;
import com.instafeeling.domain.models.Content;
import com.instafeeling.domain.services.ContentService;
import com.instafeeling.persistence.mappers.ContentMapper;
import com.instafeeling.web.api_docs.custom_responses.StandardErrorResponses;
import com.instafeeling.web.dtos.ContentDTO;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    private final DeleteContentUseCase deleteContentUseCase;
    private final LikeContentUseCase likeContentUseCase;
    private final RecommendContentUseCase recommendContentUseCase;
    private final UnlikeContentUserCase unlikeContentUserCase;

    @StandardErrorResponses
    @ApiResponse(
            responseCode = "201",
            description = "Content Created",
            content = @io.swagger.v3.oas.annotations.media.Content(schema = @Schema(implementation = ContentDTO.class))
    )
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ContentDTO> createContent(@RequestParam("file") MultipartFile file, @RequestParam("tags") String tags) throws IOException {
        Long userId = Long.parseLong((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        if (!file.isEmpty()) {
            String contentName = file.getOriginalFilename();
            String contentType = file.getContentType();
            Long contentSize = file.getSize();
            byte[] contentBytes = file.getBytes();
            // TODO: Can a content exists without tags ? if so, why are they mandatory here ?
            List<String> tagsList = Arrays.stream(tags.split(",")).toList();

            ContentDTO contentDTO = this.uploadContentUseCase.uploadContent(userId, contentName, contentType, contentSize, contentBytes, tagsList);
            return new ResponseEntity<>(contentDTO, HttpStatus.CREATED);
        }else{
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    @ApiResponse(
            responseCode = "200",
            description = "List of user content",
            content = @io.swagger.v3.oas.annotations.media.Content(array = @ArraySchema(schema = @Schema(implementation = ContentDTO.class)))
    )
    @StandardErrorResponses
    public ResponseEntity<List<ContentDTO>> getContentList(){
        Long userId = Long.parseLong((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        List<Content> contentList = this.contentService.getContent(userId);
        return ResponseEntity.ok().body(this.contentMapper.toContentDTO(contentList));
    }

    @GetMapping("/recommend")
    @ApiResponse(
            responseCode = "200",
            description = "List of content recommended to the user",
            content = @io.swagger.v3.oas.annotations.media.Content(array = @ArraySchema(schema = @Schema(implementation = Content.class)))
    )
    @StandardErrorResponses
    public ResponseEntity<List<Content>> recommendContent(){
        Long userId = Long.parseLong((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return ResponseEntity.ok().body(this.recommendContentUseCase.recommend(userId));
    }

    @GetMapping(value = "/{id}", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
    @ApiResponse(
            responseCode = "200",
            description = "Loan Content"
    )
    @StandardErrorResponses
    public ResponseEntity<byte[]> getContent(@PathVariable("id") Long contentId){
        return ResponseEntity.ok().body(this.contentService.loadUserContent(contentId));
    }

    @DeleteMapping(value = "/{id}")
    @StandardErrorResponses
    public ResponseEntity<Void> deleteContent(@PathVariable("id") Long contentId){
        Long userId = Long.parseLong((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        this.deleteContentUseCase.deleteContent(userId, contentId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/liked")
    @StandardErrorResponses
    public ResponseEntity<Void> likeContent(@PathVariable("id") Long contentId){
        Long userId = Long.parseLong((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        this.likeContentUseCase.likeContent(userId, contentId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/liked")
    @StandardErrorResponses
    public ResponseEntity<Void> unlikeContent(@PathVariable("id") Long contentId){
        Long userId = Long.parseLong((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        this.unlikeContentUserCase.unlikeContent(userId, contentId);
        return ResponseEntity.ok().build();
    }
}
