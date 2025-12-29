package com.instafeeling.application.use_cases;

import com.instafeeling.domain.ports.storage.ContentRepository;
import com.instafeeling.domain.ports.storage.ContentStorageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@AllArgsConstructor
public class DeleteContentUseCase {
    private final ContentRepository contentRepository;
    private final ContentStorageService contentStorageService;

    @Transactional(noRollbackFor = IOException.class)
    public void deleteContent(Long userId, Long contentId) {
        // validate ownership
        if (!this.contentRepository.validateOwnership(userId, contentId))
            throw new RuntimeException("You are not permitted to do this!");

        // proceed to remove content
        this.contentRepository.deleteContent(contentId);

        // remove from storage
        try {
            this.contentStorageService.deleteContent(contentId.toString());
        } catch (IOException e) {
            // maybe do some file-cleanup
            e.printStackTrace();
        }
    }
}
