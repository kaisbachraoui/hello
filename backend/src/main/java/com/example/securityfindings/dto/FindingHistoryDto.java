package com.example.securityfindings.dto;

import com.example.securityfindings.entity.FindingStatus;
import java.time.Instant;
import java.util.UUID;

public record FindingHistoryDto(
        UUID id,
        UUID findingId,
        String actor,
        FindingStatus fromStatus,
        FindingStatus toStatus,
        String action,
        String details,
        Instant createdAt
) {
}
