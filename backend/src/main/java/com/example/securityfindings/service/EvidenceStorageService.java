package com.example.securityfindings.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class EvidenceStorageService {
    private final Path storageRoot;

    public EvidenceStorageService(@Value("${app.evidence.storage-path}") String storagePath) {
        this.storageRoot = Path.of(storagePath);
    }

    public String store(UUID findingId, MultipartFile file) {
        try {
            Files.createDirectories(storageRoot);
            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path target = storageRoot.resolve(findingId.toString()).resolve(filename);
            Files.createDirectories(target.getParent());
            Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
            return target.toString();
        } catch (IOException ex) {
            throw new IllegalStateException("Unable to store evidence", ex);
        }
    }
}
