package com.instafeeling.application.use_cases;

import com.instafeeling.domain.models.Content;
import com.instafeeling.domain.ports.storage.ContentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RecommendContentUseCase {
    private final ContentRepository contentRepository;

    public List<Content> recommend(Long userId) {
        // TODO: Recommend based on user likes
        return this.contentRepository.findPopular();
    }
}
