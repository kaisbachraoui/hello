package com.example.securityfindings.dto;

import java.time.Instant;
import java.util.UUID;

public record EvidenceDto(
        UUID id,
        UUID findingId,
        String filename,
        String contentType,
        long size,
        String storagePath,
        String url,
        Instant createdAt
) {
}
