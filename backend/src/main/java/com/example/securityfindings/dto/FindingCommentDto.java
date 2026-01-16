package com.example.securityfindings.dto;

import java.time.Instant;
import java.util.UUID;

public record FindingCommentDto(
        UUID id,
        UUID findingId,
        String author,
        String comment,
        Instant createdAt
) {
}
