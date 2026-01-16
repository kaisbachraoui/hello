package com.example.securityfindings.dto;

import com.example.securityfindings.entity.FindingStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record FindingHistoryRequest(
        @NotBlank String actor,
        FindingStatus fromStatus,
        @NotNull FindingStatus toStatus,
        @NotBlank String action,
        String details
) {
}
