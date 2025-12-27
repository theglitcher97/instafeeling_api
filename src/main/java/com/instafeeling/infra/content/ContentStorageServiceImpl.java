package com.instafeeling.infra.content;

import com.instafeeling.domain.infra.ContentStorageService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class ContentStorageServiceImpl implements ContentStorageService {
    @Value("${storage.root}")
    private String rootDir;
    private Path root;

    @Override
    public void storeContent(String storageKey, byte[] content) throws IOException {
        this.root = Paths.get(rootDir);
        Path target = this.root.resolve(storageKey); // rootPath + storageKey
        Files.copy(new ByteArrayInputStream(content), target, StandardCopyOption.REPLACE_EXISTING);
    }

    @Override
    public byte[] loadContent(String storageKey) throws IOException {
        this.root = Paths.get(rootDir);
        return Files.readAllBytes(this.root.resolve(storageKey));
    }
}
