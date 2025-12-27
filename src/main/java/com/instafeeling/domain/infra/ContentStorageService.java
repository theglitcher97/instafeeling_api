package com.instafeeling.domain.infra;

import java.io.IOException;

public interface ContentStorageService {
    void storeContent(String storageKey, byte[] content) throws IOException;

    byte[] loadContent(String storageKey) throws IOException;
}
