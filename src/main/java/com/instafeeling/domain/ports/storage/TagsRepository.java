package com.instafeeling.domain.ports.storage;

import java.util.List;

public interface TagsRepository {
    void ensureExistence(List<String> tags);

    void addTagsToContent(Long id, List<String> tags);

    void deleteTags(List<String> tags);
}
