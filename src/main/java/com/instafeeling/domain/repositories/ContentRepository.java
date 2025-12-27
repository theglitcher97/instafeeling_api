package com.instafeeling.domain.repositories;

import com.instafeeling.domain.models.Content;

public interface ContentRepository {
    Content createContent(Content content);

    void deleteContent(Long id);
}
