package com.instafeeling.infra.content;

import com.instafeeling.domain.infra.ContentSystemStorage;
import org.springframework.stereotype.Service;

@Service
public class FileSystemManager implements ContentSystemStorage {
    @Override
    public void saveContent(byte[] file) {

    }
}
