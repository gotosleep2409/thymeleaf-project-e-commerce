package com.example.shop;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {
    void store(MultipartFile file);
    void init();

}
